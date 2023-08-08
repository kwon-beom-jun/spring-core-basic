package hello.core.mytest.test;

import hello.core.TestClassTest;
import hello.core.mytest.config.ConfigTest;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ServiceTestJunit {

    @Test
    public void autoWirdTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ConfigTest.class);
    }

}