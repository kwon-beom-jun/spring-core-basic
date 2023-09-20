package hello.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

/**
 * <br> TODO : Proxy 예제 및 설명-1
 * <br>     ScopedProxyMode.TARGET_CLASS : 가짜를 만들어줌
 * <br>         - 적용 대상이 인터페이스가 아닌 클래스면 'TARGET_CLASS'를 선택
 * <br>         - 적용 대상이 인터페이스면 'INTERFACE'를 선택
 * <br>     MyLoggerProxy의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관 없이
 * <br>     가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있다.
 * <br>
 * <br>     pacakge hello.core.web.proxy LogDemoProxyController 참조
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLoggerProxy {

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
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] proxy request scope bean create : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] proxy request scope bean close : " + this);
    }

}
