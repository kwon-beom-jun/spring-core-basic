package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

// TODO : 스프링 초기화 및 소멸전 함수 사용 - 인터페이스
/**     초기화, 소멸 인터페이스는 스프링이 초기화와 소멸전 사용되는 함수를 뜻함
 *
 *      단점
 *          1. 해당 인터페이스는 스프링 전용 인터페이스, 해당 코드가 스프링 전용 인터페이스 의존
 *          2. 초기화, 소멸 메서드의 이름을 변경 할 수 없다.
 *          3. 내가 코드를 고칠 수 없는 외부 라이브러리에 적용 할 수 없다.
 *
 *          참고 : 인터페이스를 사용하는 초기화, 종료 방법은 스프링 초창기에 나온 방법들이고,
 *                지금은 다음의 더 나은 방법들이 있어서 거의 사용하지 않는다.
 *
 */
public class NetworkClient_1 implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClient_1() {
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
    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
        call("Interface - 스프링이 의존관계 끝나고 호출 함수");
    }

    // 스프링이 소멸 전 호출
    @Override
    public void destroy() throws Exception {
        disconnect();
        System.out.println("Interface - 스프링이 소멸 전 호출 함수");
    }
}
