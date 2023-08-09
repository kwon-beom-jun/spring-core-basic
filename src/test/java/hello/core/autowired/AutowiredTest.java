package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

// TODO : 옵션 처리 ( @Autowired 옵션 및 @Nullabel, Optional )
/**     주입할 스프링 빈이 없어도 동작해야 할 때
 *      @Autowired는 디폴트로 required 옵션이 ture로 설정되어 있어서 자동 주입 대상이 없으면 오류가 발생
 *      @Nullable, Optional은 스프링 전반에 걸쳐서 지원
 *          - ex) 생성자 자동 주입에서 특정 필드에만 사용해도 됨
 */
public class AutowiredTest {

    @Test
    void AutowiredOption() {

        // TestBean 클래스에 어노테이션을 달지 않아도 빈으로 등록됨
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean {

        // @Autowired(required=false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
        // Member는 등록된 빈이 아님
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 == " + noBean1);
        }

        // org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 == " + noBean2);
        }

        // Optional<> : 자동 주입할 대상이 없으면 Optional.empty가 입력
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 == " + noBean3);
            System.out.println(noBean3.equals(Optional.empty()));
        }

    }


}
