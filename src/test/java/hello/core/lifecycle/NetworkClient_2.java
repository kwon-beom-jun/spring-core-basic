package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * <br>  TODO : 스프링 초기화 및 소멸전 함수 사용 - 설정 정보 (Configuration 설정에서 등록 - BeanLifeCycleTest -> LifeCycleConfig)
 * <br>       특징
 * <br>           1. 메서드 이름을 자유롭게 줄 수 있다
 * <br>           2. 스프링 빈이 스프링 코드에 의존하지 않는다
 * <br>           3. 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화,
 * <br>              종료 메서드를 적용 할 수 있다
 * <br>
 * <br>     ※ 중요 : @Bean으로 등록할때만 발생 ※
 * <br>       '종료 메서드' 추론
 * <br>           @Bean의 destoryMethod 속성에는 아주 특별한 기능이 있다.
 * <br>           라이브러리는 대부분 close, shutdown 이라는 이름의 종료 메서드를 사용한다.
 * <br>           @Bean의 destoryMethod는 기본값이 '(inferred)'(추론)으로 등록되어 있다
 * <br>               - 실제 값이 (inferred) 이렇게 스트링으로 되어있음
 * <br>
 * <br>           이 추론 기능은 'close', 'shutdown' 라는 이름의 메서드를 자동 호출해 준다.
 * <br>
 * <br>           이름 그대로 메서드를 추론해서 호출해준다
 * <br>           따라서 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 잘 작동한다.
 * <br>           추론 기능을 사용하기 싫으면 destoryMethod=""처럼 빈 공백을 지정하면 된다.
 * <br>
 */
public class NetworkClient_2 {

    private String url;

    public NetworkClient_2() {
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
    public void init() {
        connect();
        call("Configuration @Bean - 스프링이 의존관계 끝나고 호출 함수");
    }

    // 스프링이 소멸 전 호출
    public void close() {
        disconnect();
        System.out.println("Configuration @Bean - 스프링이 소멸 전 호출 함수");
    }
}
