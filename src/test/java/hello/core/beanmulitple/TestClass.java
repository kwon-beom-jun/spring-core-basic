package hello.core.beanmulitple;

import hello.core.beanmulitple.test1.AutowiredClass;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestClass {

    @Test
    public void primaryCheck() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        ac.getBean(AutowiredClass.class);

    }

}
