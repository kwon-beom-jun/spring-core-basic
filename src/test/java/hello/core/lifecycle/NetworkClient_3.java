
package hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * <br>  TODO : 스프링 초기화 및 소멸전 함수 사용 - 어노테이션으 ( 최신 스프링이 권장 : 어노테이션은 javax!! )
 * <br>       javax라서 다른 컨테이너를 사용한다해도 사용 가능
 * <br>
 * <br>       최신 스프링에서 가장 권장하는 방법
 * <br>       어노테이션 하나만 붙이면 되므로 매우 편리
 * <br>       패키지를 잘 보면 javax.annotation.PostConstruct이다. 스프링에 종속적인 기술이 아니라 JSR-250라는 자바 표준이다.
 * <br>       따라서 스프링이 아닌 다른 컨테이너에서도 사용 가능하다
 * <br>       컴포넌트 스캔과 잘 어울린다.
 * <br>
 * <br>     ※ 단점 ※
 * <br>       유일한 단점은 외부 라이브러리에 적용하지 못한다는 것이다.
 * <br>       외부 라이브러리를 초기화, 종료 해야 하면 @Bean의 기능을 사용하자
 * <br>
 * <br>     ※ 정리 ※
 * <br>       @PostConstruct, @PreDestory 어노테이션을 사용하자
 * <br>       코드를 고칠 수 없는 외부 라이브러리를 초기화, 종료해야 하면 @Bean의 initMethod, destoryMethod를 사용하자
 * <br>
 * <br>       @ComponentScan을 사용해도 잘 작동되는것 확인
 * <br>       - ComponentScan을 사용하면 BeanLifeCycleTest -> LifeCycleConfig의 생성자에 넣어주는 값들을
 * <br>         NetworkClient_3의 생성자에서 처리를 해야한다 (property로 값을 받거나 다른 매커니즘을 활용해야함)
 * <br>       예시)
 * <br>         @Service
 * <br>         public class MyServiceImpl implements MyService {
 * <br>             private final String someValue;
 * <br>             @Autowired
 * <br>             public MyServiceImpl(@Value("${some.property.value}") String someValue) {
 * <br>                 this.someValue = someValue;
 * <br>             }
 * <br>         }
 * <br>
 */
public class NetworkClient_3 {

    private String url;

    public NetworkClient_3() {
        System.out.println("생성자 호출, url = " + url);
        connect(); // url 정보 없이 connect가 호출
        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    // 연결 서버에 콜
    public void call(String message) {
        System.out.println("call : " + url + " message = " + message);
    }

    public void disconnect() {
        System.out.println("close : " + url);
    }

    // 스프링이 의존관계 끝나고 호출
    @PostConstruct
    public void init() {
        connect();
        call("Annotation - 스프링이 의존관계 끝나고 호출 함수");
    }

    // 스프링이 소멸 전 호출
    @PreDestroy
    public void close() {
        disconnect();
        System.out.println("Annotation - 스프링이 소멸 전 호출 함수");
    }
}
