package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    /**
     * FIXME :
        현재 헷갈리는점..?
        Spring Container에 @Bean으로 등록하면 들어가는것이 아니다?

      * 강사님 왈 *
        → @Bean이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록.
        → 강사님은 @Bean으로 등록된 메소드의 이름이 키 값, 메소드의 return이 값이 된다고 하셨음.
        → @Bean(name = "이름") 이러한 형식으로 키 값을 변경 할 수 있음

     // 궁금한점
        AnnotationConfigApplicationContext를 사용하여 스프링 컨테이너에 올렸다고 생각
        하지만 @Autowired를 사용하여 인스턴스 확인시 null 값으로 확인
        가설 1. class @bean 지정이랑 xml @bean 지정이랑 달라서?
                -> @bean은 외부에서 등록, xml은 직접 클래스 등록으로 알고있어서 이와같이 방식이 다르다?
                -> 하지만 그렇다면 @Conponent 달린것들은 어떻게 @Autowired를 사용 할 수 있지?
            ApplicationContext ac = new AnnotationConfigApplicationContext(Bean.class)
            @Autowired @Autowired TestClass testClass;
            (test 폴더의 TestClassTest 클래스 예시)

        → 내 생각이랑 다를 수 있는점은 Spring xml 등록시에 컨테이너에 업로드할 객체를 직접적으로 작성하여 관리해서 그럴수도 있음
     */

    public static void main(String[] args) {

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);


        Long memberId = 1l;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        System.out.println("order" + order);
        System.out.println("calculatePrice == " + order.calculatePrice());
    }
}
