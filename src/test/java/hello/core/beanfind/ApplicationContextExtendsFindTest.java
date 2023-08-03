package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

/**
 * TODO : Spring 컨테이너 빈 조회 방법-2
 *        스프링 빈 조회 시 부모 타입으로 조회하면, 자식 타입도 함께 조회 ( 컨테이너에 등록이랑은 다름 )
 *        그래서 모든 자바 객체의 최고 부모인 'Object' 타입으로 조회하면, 모든 스프링 빈을 조회 ( Spring 관련 내부 Bean들도 전부 )
 */
public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생")
    void findBeanByParentTypeDuplication(){
        // 어떤 자식을 불러오는지 지정해 주지 않으면 오류 발생
        // @Bean 컨테이너에 등록은 가능하지만 Bean을 조회 시 지정하지 않고 부모객체만 선언하여 조회 시 에러
        // 물론 @Bean 컨테이너에 등록시 메소드 명칭(Bean Name)이 같으면 에러 발생
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
        assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(Object.class));
    }

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈 이름을 지정")
    void findBeanByParentTypeBeanName(){
        DiscountPolicy rateDiscountPlicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        assertThat(rateDiscountPlicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회 : 좋은 방법 X")
    void findBeanBySubType(){
        RateDiscountPolicy bean = ac.getBean(RateDiscountPolicy.class);
        assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회")
    void findAllBeanByParentType(){
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회 - Object")
    void findAllBeanByObjectType(){
        // getBean() X , getBeansOfType() O
        Object object = ac.getBeansOfType(Object.class);
        System.out.println(object);
        System.out.println("==================================================");
        Map<String, Object> objectMap = ac.getBeansOfType(Object.class);
        for (String key : objectMap.keySet()) {
            System.out.println("key = " + key + " value = " + objectMap.get(key));
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }
        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }

}
