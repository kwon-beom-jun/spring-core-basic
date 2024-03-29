package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * <br> TODO : 프로토타입 스코프
 * <br>     1. 프로토타입 스코프의 빈을 스프링 컨테이너에 요청한다.
 * <br>     2. 스프링 컨테이너는 이 시점에 프로토타입 빈을 생성하고, 필요한 의존관계를 주입한다.
 * <br>     3. 스프링 컨테이너는 생성한 프로토타입 빈을 클라이언트에 반환한다.
 * <br>     4. 이후에 스프링 컨테이너에 같은 요청이 오면 항상 새로운 프로토타입 빈을 생성해서 반환한다
 * <br>
 * <br>      정리
 * <br>          여기서 핵심은 스프링 컨테이너는 프로토타입 빈을 생성하고, 의존관계 주입, 초기화까지만 처리한다는 것이다. 클라이
 * <br>          언트에 빈을 반환하고, 이후 스프링 컨테이너는 생성된 프로토타입 빈을 관리하지 않는다. 프로토타입 빈을 관리할 책임은
 * <br>          프로토타입 빈을 받은 클라이언트에 있다.
 * <br>      ※ 그래서 @PreDestroy 같은 종료 메서드가 호출되지 않는다 ※
 * <br>
 * <br>      - 싱글톤 빈은 스프링 컨테이너 생성 시점에 초기화 메서드가 실행 되지만, 프로토타입 스코프의 빈은 스프링 컨테
 * <br>        이너에서 빈을 조회할 때 생성되고, 초기화 메서드도 실행된다.
 * <br>      - 프로토타입 빈을 2번 조회했으므로 완전히 다른 스프링 빈이 생성되고, 초기화도 2번 실행된 것을 확인할 수 있다.
 * <br>        싱글톤 빈은 스프링 컨테이너가 관리하기 때문에 스프링 컨테이너가 종료될 때 빈의 종료 메서드가 실행되지만,
 * <br>      - 프로토타입 빈은 스프링 컨테이너가 생성과 의존관계 주입 그리고 초기화 까지만 관여하고, 더는 관리하지 않는다.
 * <br>        따라서 프로토타입 빈은 스프링 컨테이너가 종료될 때 @PreDestroy 같은 종료 메서드가 전혀 실행되지 않는다
 * <br>
 * <br>     프로토타입 빈의 특징 정리
 * <br>         - 스프링 컨테이너에 요청할 때 마다 새로 생성된다.
 * <br>         - 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입 그리고 초기화까지만 관여한다.
 * <br>         - 종료 메서드가 호출되지 않는다.
 * <br>         - 그래서 프로토타입 빈은 프로토타입 빈을 조회한 클라이언트가 관리해야 한다. 종료 메서드에 대한 호출도 클라이
 * <br>           언트가 직접 해야한다.
 * <br>
 */
public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        // PrototypeBean.class @ComponentScan처럼 등록함
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        /** 이떄 새로운 객체가 생성됨 -> 싱글톤은 이전에 init 메소드 호출 */
        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototypeBean1" + prototypeBean1);
        System.out.println("find prototypeBean2" + prototypeBean2);

        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
        
        /** 만약 destory를 사용하고자 하면 */
        System.out.println("distory 직접 실행");
        prototypeBean1.destory();
        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean init");
        }
        @PreDestroy
        public void destory() {
            System.out.println("PrototypeBean destory");
        }
    }


}
