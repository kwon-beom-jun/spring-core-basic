package hello.core.beanmulitple.test2;

import hello.core.test.autoscan.InterfaceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AutowiredClass2 {

    private final InterfaceClass interfaceClass;

    // TODO : 상속받는것이 중복 : 중복인 클래스중 사용하고자 하는 클래스를 Qualifier 지정
    /**     public AutowiredClass2(InterfaceClass QualiTest) or (InterfaceClass qualiTest)
     *      이런식으로 Qualifier 이름으로는 불가능함 [ 빈으로 등록되어있는것만 가능 ex) (InterfaceClass serviceClass3) ]
     */
    @Autowired
    public AutowiredClass2(@Qualifier("QualiTest") InterfaceClass interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

}
