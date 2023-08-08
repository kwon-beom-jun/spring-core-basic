package hello.core.mytest.service2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConstructorTest {

    private ConstructorTest2 constructorTest2;
    private ConstructorTest3 constructorTest3;

    /**
        생성자가 하나이면 Autowired 자동으로 들어감
        이때 ConstructorTest3에 빈설정이 없으면 스프링 에러가 발생
        만약 Default 생성자가 있으면 에러가 발생하지 않음
     */
    public ConstructorTest(ConstructorTest2 constructorTest2, ConstructorTest3 constructorTest3) {
        this.constructorTest2 = constructorTest2;
        this.constructorTest3 = constructorTest3;
        System.out.println("constructorTest2 == " + this.constructorTest2);
        System.out.println("constructorTest3 == " + this.constructorTest3);
    }

    public ConstructorTest() {
        System.out.println("Default 생성자");
    }

}
