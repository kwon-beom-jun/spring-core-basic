package hello.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <br> 스프링 부트 웹 라이브러리가 없으면 AnnotationConfigApplicationContext 사용
 * <br> 스프링 부트 웹 라이브러리가 있으면 AnnotationConfigServletWebServerApplicationContext 사용
 * <br> Requset, Proxy 예제 (package : web, common)
 */
@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}

}
