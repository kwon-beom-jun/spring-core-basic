package hello.core.mytest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 현재 테스트가 가능한 이유는 private final이 아님. 원래 final을 붙여서 불변 필수 의존관계를 사용해야함
 */
@Component
public class ServiceTest {

//    private final ServiceTest2 serviceTest2;
    private ServiceTest2 serviceTest2;
    private ServiceTest2 serviceTest4;

    // 이상한 메소드 명도 가능하긴함
    // 변수는 상관없음(변수는 내가 사용하려고 만드는것)
    @Autowired
    public void setAFerviceTest3(ServiceTest2 serviceTest3) {
        this.serviceTest4 = serviceTest3;
    }

    public ServiceTest () {
        System.out.println("serviceTest2 생성자 1 == " + serviceTest2);
    }

    public ServiceTest (ServiceTest2 serviceTest2) {
        System.out.println("serviceTest2 생성자 2 == " + serviceTest2);
        this.serviceTest2 = serviceTest2;
    }

}
