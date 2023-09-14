package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * <br>  TODO : Qualifier 대체용 어노테이션 생성
 * <br>      Qualifier에 선언되어있는 @Target, @Retention, @Inherited, @Documented를 @MainDiscountPolicy에서도 선언해서
 * <br>      @MainDiscountPolicy 에서도 사용하게끔 설정
 * <br>
 * <br>      @Target : Class, Method, Field 등에서 사용 가능하도록 하는 기능 등등 여러가지 기능들을
 * <br>
 * <br>      사용예시 : RateDiscountPolicy, OrderServiceImpl 생성자에서 해당 어노테이션 사용
 * <br>
 * <br>      다시 얘기하자면 어노테이션에는 상속 개념 X
 * <br>      이런 어노테이션을 모아서 사용하는 기능은 스프링이 지원해주는 기능
 * <br>      @Qualifier 뿐만 아니라 다른 어노테이션들도 조합해서 사용 할 수 있다.
 * <br>      단적으로 @Autowired도 재정의 할 수 있다
 * <br>      물론 스프링이 제공하는 기능을 뚜렷한 목적 없이 무분별하게 재정의 하는 것은 유지보수에 혼란을 가중 할 수 있다.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {

}
