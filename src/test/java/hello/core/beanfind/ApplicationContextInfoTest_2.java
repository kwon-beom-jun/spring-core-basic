package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// TODO : Spring 컨테이너 빈 조회 방법-3
class ApplicationContextInfoTest_2 {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("정상 작동 확인")
    void findAllBean(){

        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + " | Object = " + bean);
        }

        System.out.println("=========================================================");

        // TEST
        Member member = new Member(1l, "test", Grade.VIP);
        Member member2 = new Member(2l, "test2", Grade.VIP);
        
        MemberService memberService = ac.getBean("memberService", MemberService.class); // 타입을 지정 할 수 있음
        MemberService memberService2 = (MemberService) ac.getBean("memberService"); // Object로 돌려줌
        MemberService memberService3 = ac.getBean(MemberService.class); // Bean에 Return으로 설정한 구현체로 돌려줌

        memberService.join(member);
        memberService2.join(member2);
        System.out.println("memberService3 = " + memberService3.getClass());

        OrderService orderService = ac.getBean("orderService", OrderService.class);
        OrderService orderService2 = (OrderService) ac.getBean("orderService");

        System.out.println(orderService.createOrder(1l,"item1",1000).toString());
        System.out.println(orderService2.createOrder(2l,"item2",2000).toString());
    }

}
