package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <br>  TODO : ApplicationContext(스프링 컨테이너) 등록 기초
 * <br>       - 클래스에 @Configuratino을 적용해서 사용
 * <br>           new AnnotationConfigApplicationContext(AppConfig.class);
 * <br>       - XML에 Bean을 등록해서 사용하는 여러 방법
 * <br>           new ClassPathXmlApplicationContext("spring/di/setting.xml");
 * <br>           new GenericXmlApplicationContext("classpath:spring/di/setting.xml");
 * <br>
 * <br>  TODO : 컨테이너 생성 순서
 * <br>       스프링 리더클래스로 설정 정보 읽기 → 빈 메타정보 생성 → 메타정보를 바탕으로 컨테이너 생성
 * <br>       스프링 빈 등록 → 의존 관계 주입( ex - Autowired등 확인하고 주입 )
 * <br>                      물론 생성자 주입(Autowired)은 생성 될 때 필요해서 빈 등록시 주입
 * <br>
 */
@Configuration
public class AppConfig {

    // 생성자를 통해서 인스턴스를 생성하는것을 생성자 주입이라고 함
    // return은 구현체고 반환타입을 인터페이스로 돌려줌
    // test 폴더의 MemberServiceTest 클래스에 사용법 있음
//    public MemberService memberService(){ return new MemberServiceImpl(memberRepository()); }

    /**
     * <br>  ※ 에러발생
     * <br>       @Bean
     * <br>       public MemberService memberService() {
     * <br>           return new MemberServiceImpl(new MemoryMemberRepository());
     * <br>       }
     * <br>       @Bean
     * <br>       public MemberRepository memberRepository() {
     * <br>           return new MemoryMemberRepository();
     * <br>       }
     * <br>       둘다 new 해서 생성(순수 자바코드로 인스턴스 생성이라 싱클톤이 깨짐
     * <br>
     * <br>  ※ 차이점
     * <br>       return new MemberServiceImpl(new MemoryMemberRepository());
     * <br>       이 코드에서는 MemberServiceImpl을 생성할 때마다 새로운 MemoryMemberRepository 인스턴스가 생성됩니다.
     * <br>       따라서 이 방식을 사용하면 MemberServiceImpl이 여러 번 생성될 경우
     * <br>       각각의 MemberServiceImpl 인스턴스가 서로 다른 MemoryMemberRepository 인스턴스를 참조하게 됩니다.
     * <br>
     * <br>       return new MemberServiceImpl(memberRepository());
     * <br>       @Configuration 클래스 내에서 다른 @Bean 메소드를 호출하는 경우,
     * <br>       Spring은 이전에 생성된 빈 인스턴스를 재사용합니다.
     * <br>       따라서 이 코드에서는 MemberServiceImpl을 생성할 때마다
     * <br>       동일한 MemoryMemberRepository 인스턴스가 재사용됩니다.
     */

    /**
     * <br>  TODO : ConfigurationSingletonTest.java의 configurationDeep() 메소드에 정답이 있음
     * <br>     순서 예시(순서는 보장하지 않음)
     * <br>         call AppConfig.memberService | memberService()
     * <br>         call AppConfig.memberRepository | → return new MemberServiceImpl(memberRepository())
     * <br>         call AppConfig.memberRepository | memberRepository()
     * <br>         call AppConfig.orderService | orderService()
     * <br>         call AppConfig.memberRepository | → return new OrderServiceImpl(memberRepository(), ... );
     * <br>     실제로 실행 로그 (new AnnotationConfigApplicationContext(AppConfig.class); 실행)
     * <br>         call AppConfig.memberService
     * <br>         call AppConfig.memberRepository
     * <br>         call AppConfig.orderService
     */
    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }


}
