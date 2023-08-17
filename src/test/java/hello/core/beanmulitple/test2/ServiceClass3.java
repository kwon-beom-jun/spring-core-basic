package hello.core.beanmulitple.test2;

import hello.core.test.autoscan.InterfaceClass;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("QualiTest")
public class ServiceClass3 implements InterfaceClass {

}
