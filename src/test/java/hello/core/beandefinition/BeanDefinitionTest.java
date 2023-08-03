package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

// TODO : 스프링 컨테이너 생성시, BeanDefinition 의미, 빈 등록 방법 등 설명
/**
 * BeanDefinition : 빈 설정 메타정보 @Bean, <bean>당 각각 하나씩 메타정보 생성
 *                  스프링 컨테이너는 이 메타정보를 기반으로 스프링이 빈을 생성
 * 설정정보들을 AnnotatedBeanDefinitionReader, XMLBeanDefinitionReader, XxxBeanDefinitionReader 리더를 사용하여 BeanDefinition을 생성
 *
 * BeanDefinition을 등록할 때는 직접적으로 Bean을 등록하는 방법과 FactoryBean(== Java Config(AppConfig.class))을 이용하여 등록하는 방법이 있다.
 * FactoryBean을 이용하면 FactoryBean라는것을 이용해서 외부에서 등록하여 'class [null]' 등록되고 대신에 factoryBeanName과 foactoryMethodName이 등록된다.
 *      beanDefinition = Root bean: class [null]; scope=;
 *          abstract=false; lazyInit=null; autowireMode=3; dependencyCheck=0;
 *          autowireCandidate=true; primary=false; factoryBeanName=appConfig;
 *          factoryMethodName=memberService; initMethodName=null; destroyMethodName=(inferred); defined in hello.core.AppConfig
 */
public class BeanDefinitionTest {

    //    ApplicationContext 대신에 AnnotationConfigApplicationContext 사용한 이유는 .getBeanDefinition을 사용하려면 추가로 필요한 인터페이스가 있음
//    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    GenericApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                System.out.println(
                        "beanDefinitionName = " + beanDefinitionName + "\n" +
                        "beanDefinition = " + beanDefinition
                );
                System.out.println("=====================================================================");
            }
        }
    }
}
