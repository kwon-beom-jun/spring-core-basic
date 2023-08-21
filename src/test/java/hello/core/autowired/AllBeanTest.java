package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

// TODO : 조회한 빈이 모두 필요할 때 List, Map 으로 조회 / 전략 패턴 ( 예제 포함 )
public class AllBeanTest {

    @Test
    void findAllBean() {
        // DiscountService만 컨테이너에 등록하면 DiscountPolicy 및 상속받는 클래스들이 컨테이너에 등록이 되지않음
        // → AutoAppConfig.clss도 등록
        // ApplicationContext ac = new AnnotationConfigApplicationContext(DiscountService.class); // null 나옴
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        // 실무 예시
        // fixDiscountPolicy, rateDiscountPolicy 두개의 빈을 활용하여 고객의 요구사항에 맞게 사용
        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
        Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
        Assertions.assertThat(discountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        Assertions.assertThat(rateDiscountPrice).isEqualTo(2000);

    }

    static class DiscountService {

        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("==========================================================================");
            System.out.println("policyMap = " + policyMap);
            System.out.println("policyList = " + policies);
            System.out.println("==========================================================================");
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }
    }

    
}
