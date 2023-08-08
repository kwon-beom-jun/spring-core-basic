package hello.core.mytest;

import hello.core.AppConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ServiceTestJunit2 {

    public static void main(String[] args) {
        SpringApplication.run(ServiceTestJunit2.class, args);

//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ServiceTestJunit2.class);
//        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
//            // Role ROLE_APPLICATION : 외부 라이브러리나 내가 등록한 빈들
//            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
//                Object bean = ac.getBean(beanDefinitionName);
//                System.out.println("name = " + beanDefinitionName + " | Object = " + bean);
//            }
//        }

    }
}
