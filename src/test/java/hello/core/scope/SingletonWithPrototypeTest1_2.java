package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

/**
 * <br> TODO : Bean의 Scope 설정 시 Singletone과 Prototype의 문제 해결 : ObjectProvider
 * <br>     Spring Container에서 Bean을 찾아서 주입 해줌
 * <br>         → ApplicationContext.getBean(빈) 의 기능을 제공한다 생각하면됨
 * <br>         → ApplicationContext보다 경량
 * <br>         → 기능이 단순하므로 단위테스트를 만들거나 mock 코드를 만들기는 훨씬 쉬움
 * <br>         → 딱 필요한 기능만 제공
 * <br>     콘솔창을 보면 PrototypeBean이 2개의 빈이 생성된것을 확인 할 수 있음
 * <br>
 * <br>     ObjectFactory를 ObjectProvider가 상속받아서 둘 중 아무거나 사용해도 상관없음
 * <br>     하지만 Spring이 제공해주는 기능이므로 스프링에 의존적임
 * <br>
 * <br>
 * <br> TODO : Bean의 Scope 설정 시 Singletone과 Prototype의 문제 해결 : javax.inject.Provider
 * <br>     스프링에 의존하지 않는 javax.inject.Provider를 사용
 * <br>     Java 표준(JSR-330, JSR-* 은 자바 표준)이지만 gradle(maven)로 가져와야함
 * <br>
 * <br>     provider.get()을 통해서 항상 새로운 프로토타입 빈이 생성되는것을 확인 할 수 있다.
 * <br>     provider의 get()을 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다.(DL)
 * <br>     자바 표준이고, 기능이 단순하므로 단위테스트를 만들거나 mock 코드를 만들기 훨씬 쉬워진다
 * <br>     Provider는 지금 딱 필요한 DL 정도의 기능만 제공한다
 * <br>
 * <br>     get()메서드 하나로 기능이 매우 단순
 * <br>     별도의 라이브러리가 필요한 단점이 있음
 * <br>     자바 표준이므로 스프링이 아닌 다른 컨테이너에서도 사용할 수 있음
 * <br>
 * <br>
 * <br> * 두개의 공통점
 * <br>     프로토타입 뿐만 아니라 DL이 필요한 경우는 언제든 사용할 수 있다
 * <br>     지연 및 옵셔널하게 가져오거나
 * <br>     A <-> B 의존 할때 문제가 생길 수 있으므로 사용
 * <br>
 * <br> * @Lookup 어노테이션을 사용하는 방법도 있지만 이전 방법으로도 충분하고 고려해야 할 내용도 많아서 생략
 * <br>
 * <br>
 * <br> 참고 : Provider를 사용할지 ObjectProvider를 사용할 것인지 고민이 될 수 있음. ObjectProvider는
 * <br>     DL을 위한 편의 기능을 많이 제공해주고 스프링 외에도 별도의 의존관계 추가가 필요 없기 떄문에 편리.
 * <br>     만약(정말 그럴일은 없겠지만) 코드를 스프링이 아닌 다른 컨테이너에서 사용할 수 있어야 한다면
 * <br>     Provider를 사용해야함
 * <br>
 * <br>     스프링 사용하다 보면 이 기능 뿐만 아니라 다른기능들도 자바 표준과 스프링이 제공하는 기능이 겹칠때가 많다.
 * <br>     대부분 스프링이 더 다양하고 편리한 기능을 제공해 주기 때문에, 특별히 다른 컨테이너를 사용할 일이 없다면,
 * <br>     스프링이 제공해주는 기능을 사용하면 된다. ( ex - java가 지원해주는 @inject가 있음 )
 * <br>     기능을 보고 선택 → 스프링 기능이 편리하면 스프링 사용
 * <br>     기능이 비슷하고 스프링이 표준을 권장한다면(이런경우는 스프링이 처음부터 라이브러리를 들고 있는 경우가 있음)
 * <br>         → 대표적으로 @PostConstruct, @PreDestory가 있음
 * <br>
 * <br> TODO : JPA 표준을 사용하게 된 계기
 * <br>     JPA는 Hibernate를 직접 사용하기 보다는 JPA를 가지고 하고 Hibernate 구현체를 선택
 * <br>     Hibernate가 인기있을때 Hibernate 개발자를 JAVA 진영에서 데려서와 JPA라는 JAVA 표준을 만듬
 * <br>
 */
public class SingletonWithPrototypeTest1_2 {

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {
        @Autowired private Provider<PrototypeBean> prototypeBeaProvidern;
        // 둘다 동일
//        @Autowired private ObjectFactory<PrototypeBean> prototypeBeaProvidern;
//        @Autowired private ObjectProvider<PrototypeBean> prototypeBeaProvidern;
        public int logic() {
            PrototypeBean prototypeBean = prototypeBeaProvidern.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
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
