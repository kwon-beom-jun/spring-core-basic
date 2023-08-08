package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// TODO : !!!꼭 확인하기!!! ComponentScan 설명 및 스캔 등 다양한 설명이 있음

/**
 * @ComponentScan은 @Coponent가 붙은 모든 클래스를 스프링 빈으로 등록
 * @ComponentScan은 스프링 빈의 기본 이름은 클래스명을 사용하고 맨 앞글자만 소문자를 사용
 * @ComponentScan("test") 이름 부여하여 사용 가능
 * @AutoWired는 스프링 빈을 찾아서 주입 ( 이때 기본 조회 전략은 타입이 같은 빈을 찾아서 주입 )
 * getBean(MemberRepository.class)와 동일하다고 이해하면 된다.
 *
 * Default는 @Configuration 붙인 클래스 하위경로 전체 검색
 *
 * - 탐색 위치 지정 및 여러개 지정 가능
     * basePackages = {"hello.core,order", "hello.core.discount"},
 *
 * - 해당 클래스의 pacake 하위로 찾음 [ex) OrderServiceImpl : hello.core.order 하위]
     * basePackageClasses = OrderServiceImpl.class
 *
 * Component 스캔 대상 ( 각각의 어노테이션 내부에  @Component가 설정되어있어서 가능 )
     * 1. @Component : 컴포넌트 스캔에서 사용
     * 2. @Controller : 스프링 MVC 컨트롤러에서 사용
     * 3. @Service : 스프링 비즈니스 로직에서 사용
     * 4. @Repository : 스프링 데이터 접근 계층에서 사용
     * 5. @Configuration : 스프링 설정 정보에서 사용
 *
 * ※ 주의 ※
     * - 어노에티션은 상속 관계가 없음
     * - 어노테이션과 어노테이션이 붙어있다해서 연동되지 않음
     * - 어노테이션과 특정 어노테이션을 들고 있는것을 인식 할 수 있는것은
     * 자바 언어가 지원하는 기능이 아니고, 스프링이 지원하는 기능이다.
     * ex) @Service( public @interface Service ) 내부에 @Component 연관관계가 있는것은 스프링 기능
 *
 * 어노테이션은 매타정보이고 스프링은 부가기능을 추가하여 사용
     * 1. @Controller : 스프링 MVC 컨트롤러로 인식
     * 2. @Repository : 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링에 추상화된 예외로 변환해준다
                     * DB마다 예외처리하는 방식이 다른데 만약 이렇게 되면 서비스 계층 및 다른 계층의 코드까지 흔들린다
                     * 하여 스프링이 그렇게 안되도록 예외를 추상화하여 반환해준다
                     * DB에 접근하는 예외를 스프링 예외로 변환해 주는 역할도 한다
     * 3. @Configuration : 앞서 보았듯이 스프링 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리를 한다
     * 4. @Service : 사실 특별한 처리는 하지 않는다. 대신 개발자들이 핵심 비즈니스 로직이 여기에 있구나 라고
                    * 비지니스 계층을 인식하는데 도움을 준다
 *
 * 참고 : useDefaultFilters 옵션은 기본으로 켜져있는데, 이 옵션을 끄면 기본 스캔 대상들이 제외된다.
 */
@Configuration
@ComponentScan(
        // @Configuration 붙은 것은 ComponentScan에서 제외 ( AppConfig.class 및 여러개의 테스트 클래스에도 만듬 )
        // @Configuration 들어가보면 @Component가 붙어있음
        // 보통 실무에서 할 때는 제외하지는 않음. 다만 현재 기존 예제 코드를 남기고 의도하지 않은 동작이 생길 수 있어서 필터 사용
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)


)
public class AutoAppConfig {

    // TODO : 빈 등록 시 중복 등록 테스트
    /**
     
     !! 자동빈 vs 자동빈 : ConflictingBeanDefinitionException 예외 발생
     
     !! 수동빈 VS 자동빈 : 수동빈이 우선권을 가짐 ( 수동빈이 자동빈을 오버라이드함 )
        ※ SpringBoot는 기본 값이 수동, 자동이 충돌이 나면 오류가 발생하도록 변경됨 (기본값을 변경하여 오버라이딩 되도록 할 수 있음)
            SpringBoot ERROR LOG
            - Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
          아래의 Bean(MemberRepository memberRepository()) 설정을 활성화 후 CoreAppliction 클래스를 돌리면 에러가남

     AutoAppConfigTest에서 테스트시 Spring 빈 등록시 나오는 로그
     Overriding bean definition for bean 'memoryMemberRepository' with a different definition
          : replacing [Generic bean: class [hello.core.member.MemoryMemberRepository];
            scope=singleton; abstract=false; lazyInit=null; autowireMode=0; dependencyCheck=0;
            autowireCandidate=true; primary=false; factoryBeanName=null;
            factoryMethodName=null; initMethodName=null; destroyMethodName=null;
            defined in file
            [C:\Users\qjawn\Desktop\intellij\spring-basic(inflearn)\pjt\core\out\production\classes\hello\core\member\MemoryMemberRepository.class]]
            with [Root bean: class [null]; scope=; abstract=false; lazyInit=null; autowireMode=3;
            dependencyCheck=0; autowireCandidate=true; primary=false; factoryBeanName=autoAppConfig;
            factoryMethodName=memberRepository; initMethodName=null; destroyMethodName=(inferred);
            defined in hello.core.AutoAppConfig]


    **/
    /*@Bean(name = "memoryMemberRepository")
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }*/


/**
 *  OrderSerivceImpl.java 필드 주입 @Configuration 설명 적어둠
 *
 */
//  방법 1.
//    @Autowired MemberRepository memberRepository;
//    @Autowired DiscountPolicy discountPolicy;
//    @Bean
//    OrderService orderService() {
//        return new OrderServiceImpl(memberRepository, discountPolicy);
//    }
//  방법 2.
//    @Bean
//    OrderService orderService(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        System.out.println("memberRepository ========= " + memberRepository);
//        System.out.println("discountPolicy ========= " + discountPolicy);
//        return new OrderServiceImpl(memberRepository, discountPolicy);
//    }

}








