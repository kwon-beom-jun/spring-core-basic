package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class SingletonTest {

    @Test
    void singletonBeanFind() {
        // SingletonTest.class @ComponentScan처럼 등록함
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletoneBean.class);
        System.out.println("객체 생성이 이미 되어있음");
        SingletoneBean singletoneBean1 = ac.getBean(SingletoneBean.class);
        SingletoneBean singletoneBean2 = ac.getBean(SingletoneBean.class);
        Assertions.assertThat(singletoneBean1).isSameAs(singletoneBean2);
        ac.close();
    }

    @Scope("singleton") // default라 안해도 되지만 공부하기위한 표시
    static class SingletoneBean {
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean init");
        }
        @PreDestroy
        public void destory() {
            System.out.println("SingletonBean destory");
        }
    }

}
