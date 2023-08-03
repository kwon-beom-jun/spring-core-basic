package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * AppConfig.class 참조
 */
public class ConfigurationSingletonTest {

    @Test
    void configurationTest () {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // !! memberService memberService = ac.getBean("memberService", MemberService.class);
        // MemberService로 하지 않는 이유는 Impl에 테스트 메소드 설정
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        
        // memberService, orderService, 빈 컨테이너의 memberRepository : 셋다 동일한것을 참조함
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        System.out.println(memberRepository1 == memberRepository2);
        System.out.println(memberRepository1 == memberRepository);

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }

    /**
     * TODO : AppConfig.java에서 일반 java의 new 메소드들이 있어도 싱글톤으로 유지되는 이유
     *        @Configuration 사용 이유
     */
    @Test
    void configurationDeep() {
        // AnnotationConfigApplicationContext 사용하면 Appconfig도 Bean으로 등록된다.
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        /**
         * 원래 출력은 class hello.core.Appconfig로 되어야 하지만
         * 출력 결과는 class hello.core.AppConfig$$EnhancerBySpringCGLIB$$68989ba6로 되어있음
         * 이유는 @Configuration에 있음.
         *
         * 스프링이 new AnnotationConfigApplicationContext(AppConfig.class); 에서 bean으로 등록하면서
         * CGLIB라는 바이트코드 조작 라이브러리를 사용해 AppConfig 클래스를 상속받아 임의의 다른 클래스를 만들고,
         * 그 다른 클래스를 스프링 빈으로 등록
         * 이름은 appConfig로 등록되지만 인스턴스는 AppConfig@CGLIB로 등록되어있음
         * 해당 임의의 다른 클래스가 싱글톤이 보장되도록 해줌
         *
         * AppConfig@CGLIB는 AppConfig의 자식타입이므로, AppConfig 타입으로 조회 가능하다.
         * (appConfig로 등록도 되어있음)
         * 
         * AOP도 동일한 매커니즘을 사용함
         *
         * @Configuration을 적용하지 않는다면
         * bean = class hello.core.AppConfig 및 싱글톤이 보장되지 않아 memberRepository가 여러번 출력되는것을 볼 수 있다.
         * new AnnotationConfigApplicationContext(AppConfig.class)에서 AppConfig는 Bean으로 등록이 됨
         * → 실제로 Spring Bean이 등록이 됨
         * → configurationTest() 돌려보면 에러남
         *      → 해당 메소드 안에 MemberServiceImpl 클래스의 private final MemberRepository memberRepository; 인스턴스 생성은
         *        Spring Bean으로 생성된 것이 아닌 new로 생성된것임(Spring Container가 관리하지 않음) / OrderServiceImpl도 동일
         */
        System.out.println("bean = " + bean.getClass());



    }

}
