package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

/**
 * <br> TODO : Request 스코프 예제 및 설명
 * <br>     HTTP 요청 당 하나씩 인스턴스가 생성
 * <br>     requestURL은 이 빈이 생성되는 시점에는 알 수 없으므로 외부에서 setter로 주입
 * <br>
 * <br>     Requset 스코프 예제
 * <br>         - 동시에 여러 HTTP 요청이 오면 어떤 요청이 남긴 로그인지 구분하기 어려움 이때 사용 예제
 * <br>           기대하는 공통 포멧 : [UUID][requestURL]{message}
 * <br>
 * <br>     Request 스코프 사용 이유
 * <br>         - 모든 정보를 서비스 계층에 넘기면 가능은 하나 파라미터가 많아져서 코드가 지저분해진다
 * <br>           또한 requestURL 같은 웹과 관련된 정보가 웹과 관련없는 서비스 계층까지 넘어가게 된다.
 * <br>         - 웹과 관련된 부분은 컨트롤러 까지만 사용해야한다
 * <br>           서비스 계층은 웹 기술에 종속되지 않고, 가급적 순수하게 유지하는 것이 유지보수 관점에 좋다
 */
@Component
@Scope(value = "request") // @Scope("request") 이렇게 사용 가능
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        String uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }

}
