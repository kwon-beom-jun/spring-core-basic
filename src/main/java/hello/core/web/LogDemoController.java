package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <br> 주의 사항
 * <br>      Provider, ObjectProvider, ObjectFactory 등을 사용하지 않고 실행해서 Bean이 생성될 때
 * <br>      MyLogger는 스코프가 request라서 요청사항이 들어올때만 Bean에 등록되는데 의존주입을 하려 해서 에러가 난다
 * <br>      - ERROR MESSAGE
 * <br>        ... Error creating bean with name 'myLogger': Scope 'request' is not active for the current thread;
 * <br>        consider defining a scoped proxy for this bean if you intend to refer to it from a singleton; ...
 * <br> 참고
 * <br>     requestURL을 MyLogger에 저장하는 부분은 컨트롤러 보다는 공통 처리가 가능한 스프링 인터셉터나
 * <br>     서블릿 필어 같은 곳을 활용하는 것이 좋다. 여기는 예제를 단순화했다
 */
@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    // private final MyLogger myLogger; 에러 발생
    private final ObjectProvider<MyLogger> myLoggerObjectProvider;

    /**
     * HttpServletRequest : JAVA에서 제공하는 표준 서블릿 규약
     */
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURI = request.getRequestURI().toString(); // 값 : /log-demo
        String requestURL = request.getRequestURL().toString(); // 값 : http://localhost:8080/log-demo

        System.out.println("requestURI : " + requestURI);
        System.out.println("requestURL : " + requestURL);

        /**
         * <br> Provider을 사용하여 request scope 빈의 생성을 지연
         * <br> (스프링 컨테이너에게 요청을 지연하는것이고 스프링 빈이 그 시점에 생성해주는것)
         * <br>     → ObjectProvider.getObject 호출하는 시점에 HTTP 요청이 진행중이므로
         * <br>       request scope 빈의 생성이 정상처리
         * <br>
         * <br> ObjectProvider.getObject()를 LogDemoController, LogDemoService에서
         * <br> 각각 한번씩 따로 호출해도 같은 HTTP 요청이면 같은 스프링 빈이 반환
         * <br>     ex) 코드 실행시 콘솔 로그
         * <br>         request scope bean create : 참조값 == request scope bean close : 참조값
         */
        MyLogger myLogger = myLoggerObjectProvider.getObject(); // 추가

        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId");
        return "OK";
    }

}
