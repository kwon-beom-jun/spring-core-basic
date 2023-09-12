package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * <br> TODO : Bean의 Scope 설정 시 Singletone과 Prototype의 주의사항
 * <br>     스프링은 일반적으로 싱글톤 빈을 사용하므로, 싱글톤 빈이 프로토타입 빈을 사용하게 된다.
 * <br>     그런데 싱글톤 빈은 생성 시점에만 의존관계 주입을 받기 때문에, 프로토타입 빈이 새로 생성되기는 하지만,
 * <br>     싱글톤 빈과 함께 계속 유지되는 것이 문제다.
 * <br>
 * <br>     * 참고
 * <br>     여러 빈에서 같은 프로토타입 빈을 주입 받으면, *주입 받는 시점에 각각 새로운 프로토타입 빈이 생성*된다.
 * <br>     예시) clientA, clientB가 각각 의존관계 주입을 받으면 각각 다른 인스턴스의 프로토타입 빈을 주입받는다.
 * <br>         clientA : prototypeBean@x01
 * <br>         clientB : prototypeBean@x02
 * <br>         물론 사용할 때 마다 새로 생성되는 것은 아니다
 * <br>
 * <br>     원하는것은 사용할 때 마다 새로 생성해서 사용하는것을 원한다.
 * <br>     무식한 방법으로는
 * <br>         @Autowired ApplicationContext context을 설정
 * <br>         loginc() 함수를 호출때마다 context.getBean을 이용하여
 * <br>         PrototypeBean을 스프링이 요청 때마다 주입하는 방법이 있지만 좋은 코드가 아님
 * <br>
 */
public class SingletonWithPrototypeTest1 {

    // ClientBean에서 처음 객체 생성시점에 스프링이 Client 내부의 PrototypeBean을 주입함
    // 이후 ClientBean 내부의 PrototypeBean은 같은것을 사용
    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(2);

    }

    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean; // 생성시점에 스프링이 주입하고 관리안함
        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }
        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    // ----------- ----------- ----------- ----------- ----------- ----------- ----------- -----------

    // 싱글톤 없이 프로토타입 스코프만 가지고 있을때 정상적동 로직
    @Test
    void prototypeFind() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        Assertions.assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        Assertions.assertThat(prototypeBean2.getCount()).isEqualTo(1);

    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;
        public void addCount() {count++;}
        public int getCount() {return count;}
        @PostConstruct
        public void init() { System.out.println("PrototypeBean.init " + this);}
        @PreDestroy
        public void destory() {System.out.println("PrototypeBean.destory");}// Scope Prototype이라 호출 X
    }
}
