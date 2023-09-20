package hello.core.web.proxy;

import hello.core.common.MyLoggerProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <br> TODO : Proxy 예제 및 설명-2
 * <br> Proxy 객체
 * <br>     MyLoggerProxy가 빈으로 생성될때
 * <br>     hello.core.common.MyLoggerProxy$$EnhancerBySpringCGLIB$$2dff314a 생성되는것을 확인 할 수 있음
 * <br>     ( @Configration, new AnnotationConfigApplicationContext(빈 설정 객체) 등 컨테이너에 등록되는
 * <br>       '빈 설정 객체'도 CGLIB를 사용한 것으로 기억 )
 * <br>     CGLIB가 붙은것은 스프링이 조작하여 생성
 * <br>
 * <br>     → 진짜 MyLoggerProxy 아닌 가짜로 껍데기가 생성되고 처음 기능을 호출하는 시점에 진짜를 찾아서 집어넣어줌
 * <br>        ( Provider가 동작했던 것처럼 동작이 이루어짐 )
 * <br>     → 스프링 AOP도 아래의 프록시 원리와 비슷하게 동작
 * <br>
 * <br>     결국 CGLIB라는 라이브러리로 내 클래스를 상속 받는 가짜 프록시 객체를 만들어서 주입
 * <br>         - @Scope의 proxyMode = ScopedProxyMode.TARGET_CLASS를 설정하면 스프링 컨테이너는 CGLIB라는
 * <br>           바이트 코드를 조작하는 라이브러리를 사용해서, MyLogger를 상속받은 가짜 프록시 객체를 생성
 * <br>         - 결과를 확인해보면 우리가 등록한 순수항 MyLogger 클래스가 아니라
 * <br>           MyLoggerProxy$$EnhancerBySpringCGLIB$$2dff314a이라는 클래스로 만들어진 객체가 대신 등록된 것을 확인
 * <br>         - 스프링 컨테이너에 myLoggerPorxy라는 이름으로 진짜 대신에 이 가짜 프록시 객체를 등록
 * <br>         - ac.getBean("myLoggerProxy", MyLoggerProxy.class)로 조회해도 프록시 객체가 조회되는것을 확인
 * <br>         - 그래서 의존관계 주입도 이 가짜 프록시 객체가 주입
 * <br>
 * <br>     가짜 프록시 객체는 요청이 오면 그때 내부에서 빈을 요청하는 위임 로직이 들어있음
 * <br>         - 가짜 프록시 객체는 내부에 진짜 myLoggerProxy를 찾는 방법을 알고있다.
 * <br>         - 클라이언트가 myLoggerProxy.logic()을 호출하면 사실은 가짜 프록시 객체의 메서드를 호출한 것이다.
 * <br>         - 가짜 프록시 객체는 request 스코프의 진짜 myLoggerProxy.logic()을 호출한다.
 * <br>         - 가짜 프록시 객체는 원본 클래스를 상속 받아서 만들어졌기 때문에 이 객체를 사용하는 클라이언트 입장에서는
 * <br>           사실 원본인지 아닌지 모르게, 동일하게 사용할 수 있다(다향성)
 * <br>
 * <br>     동작 정리
 * <br>         1. CGLIB라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
 * <br>            (가짜기 때문에 request 스코프와 필요(상관)이 없고 마치 싱글톤처럼 등록되어서 다른곳에서도 공유하여 사용이 가능)
 * <br>         2. 이 가짜 프록시 객체는 실제 요청이 오면 그때 내부에서 실제 빈을 요청하는 위임 로직이 들어있다.
 * <br>         3. 가짜 프록시 객체는 실제 request scope와 관계가 없다. 그냥 가짜이고, 내부에 단순한 위임 로직만 있고,
 * <br>            싱글톤 처럼 동작한다.
 * <br>
 * <br>     특징 정리
 * <br>         1. 프록시 객체 덕분에 클라이언트는 마치 싱글톤 빈을 사용하듯이 편리하게 request scope를 사용할 수 있다.
 * <br>         2. 사실 Provider를 사용하든, 프록시를 사용하든 핵심 아이디어는 진짜 객체 조회를 꼭 필요한 시점까지
 * <br>            지연처리 한다는 점이다.
 * <br>         3. 단지 어노테이션 설정 변경만으로 원본 객체를 프록시 객체로 대처 할 수 있다.
 * <br>            이것이 바로 다향성과 DI 컨테이너가 가진 가장 큰 강점이다.
 * <br>         4. 꼭 웹 스코프가 아니어도 프록시를 사용할 수 있다.
 * <br>
 * <br>     주의점
 * <br>         - 마치 싱글톤을 사용하는것 같지만 다르게 동작하기 때문에 결국 주의해서 사용해야 한다.
 * <br>           → private final MyLoggerProxy myLoggerProxy만 보면 싱글톤처럼 보이지만 requset scope
 * <br>         - 이런 특별한 scope는 꼭 필요한 곳에만 최소화해서 사용하자, 무분별하게 사용하면 유지보수하기 어려워진다.
 * <br>
 * <br>     ※ 프록시를 사용하면 스프링이 조작하여 생성되는것 말고 진짜도 컨테이너에 내부적으로 다른이름으로 생성된다고 말씀하셨지만
 * <br>       자세한 내용은 너무 디테일한 내용이라 말씀해 주시지 않는다 하심
 */
@Controller
@RequiredArgsConstructor
public class LogDemoProxyController {

    private final LogDemoProxyService logDemoService;
    private final MyLoggerProxy myLoggerProxy;

    @RequestMapping("log-demo-proxy")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURI = request.getRequestURI().toString(); // 값 : /log-demo-proxy
        String requestURL = request.getRequestURL().toString(); // 값 : http://localhost:8080/log-demo-proxy

        System.out.println("proxy requestURI : " + requestURI);
        System.out.println("proxy requestURL : " + requestURL);

        System.out.println("myLoggerProxy 프록시 객체 >>>>>>> " + myLoggerProxy);
        // class hello.core.common.MyLoggerProxy$$EnhancerBySpringCGLIB$$2dff314a
        System.out.println("myLoggerProxy 프록시 객체 >>>>>>> " + myLoggerProxy.getClass());

        // 처음 기능이 호출되는 시점
        myLoggerProxy.setRequestURL(requestURL);
        myLoggerProxy.log("proxy controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId");
        return "OK";
    }

}
