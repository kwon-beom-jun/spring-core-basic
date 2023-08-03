package hello.core.scan.filter;

import java.lang.annotation.*;


// TODO : 어노테이션 생성-1
@Target(ElementType.TYPE) // ElementType.TYPE : TYPE은 클래스 레벨에 붙음
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent { // MyIncludeComponent 얘가 붙은것은 ComponentScan에 추가 할 것이다
}
