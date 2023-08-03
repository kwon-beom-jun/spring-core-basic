package hello.core.scan.filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

    @Test
    void filterScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
        Assertions.assertThat(beanA).isNotNull();

        // NoSuchBeanDefinitionException 클래스인지 아는 방법은 에러가 나는 최상단에 클래스명이 나옴
        // Error : org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type 'hello.core.scan.filter.BeanB' available
        // ac.getBean(BeanB.class); → 실행 시 예외 발생

        org.junit.jupiter.api.Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                () -> ac.getBean("beanB", BeanB.class)
        );

    }

    // FIXME : 이상하게 import static으로 변경하면 맨 앞의 @ComponentScan는 에러가 남
    //  → import org.springframework.context.annotation.ComponentScan; 추가해줌
    /**
     * import static org.springframework.context.annotation.ComponentScan.*;
     * 원래 @ComponentScan.Filter 이것인데 import static으로 변경해서 사용 가능
     *
     * FilterType 옵션
     *      ANNOTATION : 기본값, 어노테이션을 인식해서 동작 ( type = FilterType.ANNOTATION : 기본값이여서 생략 가능 )
     *      ASSIGNABLE_TYPE : 지정한 타입과 자식 타입을 인식해서 동작 ( 클래스 직접 지정 )
     *          ex) 아래 예제 참조
     *      ASPECTJ : AspectJ 패턴 사용
     *      REGEX : 정규 표현식
     *      CUSTOM : TypeFilter 이라는 인터페이스를 구현해서 처리
     *
     *  ※ 참고 : @Component면 충분하기 때문에 includeFilterㄴ를 사용할 일은 잘 없다.
     *           excludeFilters는 여러가지 이유로 간혹 사용할 때가 있지만 많지는 않다
     *           특히 최근 스프링 부트는 컴포넌트 스캔을 기본으로 제공하는데, 개인적으로는 옵션을 변경하면서
     *           사용하기 보다는 스프링의 기본 설정에 최대한 맞추어 사용하는 것을 권장하고, 선호하는 편이다.
     *
    */
    @Configuration
    @ComponentScan(
            includeFilters = @Filter(classes = MyIncludeComponent.class),
            excludeFilters = {
                    @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class),
                    // @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = BeanA.class) 제외됨
            }
    )
    static class ComponentFilterAppConfig {

    }

}
