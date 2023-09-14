package hello.core.beanmulitple.test3;

import hello.core.test.autoscan.InterfaceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class AutowiredClass3 {

    private final InterfaceClass interfaceClass;

    /**
     * <br> TODO : 상속받는것이 중복 : 중복인 클래스중 사용하고자 하는 클래스를 Primary(우선순위) 지정
     * <br>      Primary 사용 예시
     * <br>      메인 데이터베이스 커넥션을 획득하는 스프링 빈이 있고, 코드에서 특별한 기능으로 가끔 사용하는 서브 데이터베이스
     * <br>      커넥션을 획득하는 스프링 빈이 있다고 가정하면 메인 데이터베이스 커넥션을 획득하는 스프링 빈은 @Primary
     * <br>      서브 데이터베이스 커넥션 빈을 획득 할 때는 @Qualifier를 지정하여 명시적으로 획득하는 방식으로 하면
     * <br>      코드를 깔끔하게 유지 할 수 있음
     */
    @Autowired
    public AutowiredClass3(InterfaceClass interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

}
