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


//  TODO : ApplicationContext(스프링 컨테이너) 등록 기초
/**
 * - 클래스에 @Configuratino을 적용해서 사용
 *      new AnnotationConfigApplicationContext(AppConfig.class);
 * - XML에 Bean을 등록해서 사용하는 여러 방법
 *      new ClassPathXmlApplicationContext("spring/di/setting.xml");
 *      new GenericXmlApplicationContext("classpath:spring/di/setting.xml");
 */
//  TODO : 컨테이너 생성 순서
/**      스프링 리더클래스로 설정 정보 읽기 → 빈 메타정보 생성 → 메타정보를 바탕으로 컨테이너 생성
 *       스프링 빈 등록 → 의존 관계 주입( ex - Autowired등 확인하고 주입 )
 *                      물론 생성자 주입(Autowired)은 생성 될 때 필요해서 빈 등록시 주입
 *
 */
@Configuration
public class AppConfig {

    // 생성자를 통해서 인스턴스를 생성하는것을 생성자 주입이라고 함
    // return은 구현체고 반환타입을 인터페이스로 돌려줌
    // test 폴더의 MemberServiceTest 클래스에 사용법 있음
//    public MemberService memberService(){ return new MemberServiceImpl(memberRepository()); }

    /**
     * ※ 에러발생
     *      @Bean
     *      public MemberService memberService() {
     *          return new MemberServiceImpl(new MemoryMemberRepository());
     *      }
     *      @Bean
     *      public MemberRepository memberRepository() {
     *          return new MemoryMemberRepository();
     *      }
     *      둘다 new 해서 생성(순수 자바코드로 인스턴스 생성이라 싱클톤이 깨짐   */

    /**
     * TODO : ConfigurationSingletonTest.java의 configurationDeep() 메소드에 정답이 있음
     * 순서 예시(순서는 보장하지 않음)
     *      call AppConfig.memberService | memberService()
     *      call AppConfig.memberRepository | → return new MemberServiceImpl(memberRepository())
     *      call AppConfig.memberRepository | memberRepository()
     *      call AppConfig.orderService | orderService()
     *      call AppConfig.memberRepository | → return new OrderServiceImpl(memberRepository(), ... );
     * 실제로 실행 로그 (new AnnotationConfigApplicationContext(AppConfig.class); 실행)
     *      call AppConfig.memberService
     *      call AppConfig.memberRepository
     *      call AppConfig.orderService
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
