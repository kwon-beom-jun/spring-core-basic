package hello.core.beanmulitple.test3;

import hello.core.test.autoscan.InterfaceClass;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ServiceClass5 implements InterfaceClass {

}
