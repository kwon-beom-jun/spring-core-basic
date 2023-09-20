package hello.core.web.proxy;

import hello.core.common.MyLoggerProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoProxyService {

    private final MyLoggerProxy myLoggerProxy;

    public void logic(String id) {
        myLoggerProxy.log("proxy service id = " + id);
    }

}
