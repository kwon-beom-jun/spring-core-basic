package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TODO : 스프링 빈 라이프사이클
/**     객체를 생성해 놓고 → 의존관계 주입 (setter, field)
 *      * 생성자 주입은 예외 : 생정자 주입은 객체를 만들때 이미 스프링 빈이 파라미터에 들어와야 하기 때문에 예외
 *
 *      스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용 할 수 있는 준비가 완료
 *      따라서 초기화 작업은 의존관계 주입이 모두 완료되고 난 다음에 호출해야 한다.
 *      그런데 개발자가 의존관계 주입이 모두 완료된 시점을 어떻게 알 수 있을까?
 *      * 초기화 : 객체를 생성하는 작업이 아닌 객체 안에 필요한 값들의 연결이 다 되어있는 상태에서 외부랑 연결 및 객체가 일을 처음하는 시점
 *
 *      ※ 스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공한다.
 *      또한 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백(일반적인 싱글톤, 다른 스코프는 또 다른 콜백을 받음)을 준다
 *      따라서 안전하게 작업을 진행 할 수 있다.
 *
 *      스프링 빈의 이벤트 라이프사이클(싱글톤에 대한 라이프사이클, 다른 라이프사이클이 있음)
 *          스프링 컨테이너 생성 → 스프링 빈 생성(생성자 주입은 이시점에 어느정도 일어남) → 의존관계 주입
 *          → 초기화 콜백 → 사용 → 소멸전 콜백 → 스프링 종료
 *
 *      초기화 콜백 : 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
 *      소멸전 콜백 : 빈이 소멸되기 직전에 호출
 *
 *      스프링은 다양한 방식의 생명주기 콜백을 지원한다.
 *
 *      ※ 참고 ※
 *      객체의 생성과 초기화를 분리하자.
 *      생성자는 필수 정보(파라미터)를 받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다.
 *      반면에 초기화는 이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는 등 무거운 동작을 수행.
 *      따라서 생성자 안에서 무거운 초기화 작업을 함께 하는 것 보다는 객체를 생성하는 부분과
 *      초기화 하는 부분을 명확하게 나누는 것이 유지보수 관점에서 좋다.
 *      물론 초기화 작업이 내부 값들만 약간 변경하는 정도로 단순한 경우에는 생성자에 한번에 다 처리하는게 나을수도 있다.
 *
 *      단일책임의 원칙 : 객체 인스턴스가 new 해서 자바에서 생성(메모리할당)되는것까지만 집중. 필요 데이터 생성까지
 *                      초기화는 동작하는것임(외부와 커넥션 등 동작 행위)
 *
 *      생성자는 객체 내부의 값 셋팅 등 이정도의 레벨만 하는것이 좋음
 *
 *      이렇게 진행하면 유지보수에도 좋음
 *      또한 예시로 객체 생성 후 실제 외부 커넥션 맺거나 하는것은 최초의 어떤 행위가 올때까지 미룰 수 있음
 *      생성만 하고 기다리다가 최초의 액션이 들어올대 그떄 초기화를 호출하는 형식으로 로직을 구현 할 수 있다.
 *      (이렇게는 잘 하지 않음)
 *
 *      스프링은 크게 3가지 방법으로 생명주기 콜백을 지원
 *      - 인터페이스(InitializingBean, DisposableBean)
 *      - 빈 등록 설정 정보에 초기화 메서드, 종료 메서드 지정
 *      - @PostConstruct, @PreDestory 어노테이션 지원
 *      ( 콜백 예시 : 이제 소멸되니까 커넥션 미리 닫아라고 알려줌 )
 *
 *
 */
public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
//        ApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        /** AnnotationConfigApplicationContext의 조상이 ConfigurableApplicationContext이다.
         *  AnnotationConfigApplicationContext ->...->...-> ConfigurableApplicationContext */
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient_3 client = ac.getBean(NetworkClient_3.class);
        ac.close(); // ApplicationContext 기본 인터페이스에서 지원하지 않음
    }

    @Configuration
    static class LifeCycleConfig {

        /**
         * 추론 기능은 destroyMethod만 가능!! (init X)
         */
        // destroyMethod의 close는 없어도 추론기능으로 사용됨
        @Bean//(initMethod = "init" /*, destroyMethod = "close"*/ )
        public NetworkClient_3 networkClient_3() {
            NetworkClient_3 networkClient = new NetworkClient_3();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

    }
}
