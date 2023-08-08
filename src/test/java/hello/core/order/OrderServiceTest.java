package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder(){
        // TODO : primivite 타입은 null을 사용 할 수 없고 데이터베이스에서 null 값이 추출 될 수 있어서 레퍼클래스로 변경
        Long memberId = 1l;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    // OrderServiceImpl의 필드 주입 부분 설명
    //      필드 주입 사용 시 원하는 레파지토리를 설정 할 수 없음
    // 스프링 컨테이너 띄워야함
    @Test
    void fileInjectionTest() {
//        OrderServiceImpl orderService1 = new OrderServiceImpl();
        // 필드 주입을 하지 못해서 필드 변수를 사용 할 때 NullPointException 에러 발생
        // orderService1.createOrder(1L, "itemB", 30000);
    }

}
