package hello.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class TestClassTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    @Autowired
    TestClass testClass;

    @Test
    @DisplayName("Auto Test")
    public void autoTest() {
        System.out.println(testClass);
    }

    @Test
    @DisplayName("깡통 Bean TEST")
    public void TEST(){
        TestClass bean = ac.getBean(TestClass.class);
        Assertions.assertThat(bean).isInstanceOf(TestClass.class);
    }

    @Configuration
    public static class TestBean {
        @Bean
        public TestClass beanTest(){
            return new TestClass();
        }
    }

}
