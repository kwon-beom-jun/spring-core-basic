package hello.core.beanmulitple.test1;

import hello.core.test.autoscan.InterfaceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutowiredClass {

    private final InterfaceClass interfaceClass;

    // TODO : 상속받는것이 중복 : 중복인 클래스중 사용하고자 하는 클래스의 Bean 이름으로 파라미터 변수의 이름으로 사용
    /**     ex) 필드 @Autowired private final InterfaceClass interfaceClass; 으로 선언되어있을때 중복일경우
     *          @Autowired private final InterfaceClass serviceClass1; 으로 사용 가능
     *          타입 매칭시 중복이면 필드 이름으로 빈 이름을 추가적으로 매칭함
     *      ex) 아래 예시로 상속받는것중 하나인 ServiceClass1 클래스의 빈 이름인 serviceClass1으로 사용
     */
    @Autowired
    public AutowiredClass (InterfaceClass serviceClass1) {
        this.interfaceClass = serviceClass1;
    }

}
