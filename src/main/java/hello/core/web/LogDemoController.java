package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
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
    private final MyLogger myLogger;

    /**
     * HttpServletRequest : JAVA에서 제공하는 표준 서블릿 규약
     */
    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURI().toString();
        myLogger.setRequestURL(requestURL);
        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }

}
