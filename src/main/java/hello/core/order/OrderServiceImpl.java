package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TODO : RequiredArgsConstructor 이란?
//      필드에 final로 선언되어있는 값들을 포함하여 생성자를 만들어줌
//      컴파일 시점에 생성자 코드를 자동으로 생성
@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
//    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

//    TODO : 해당 @Autowired는 해당 클래스가 SpringBean에 등록되어 있어야만 사용 할 수 있다
//          만약 OrderServiceImpl이 SpringBean에 등록되어있지 않다면 내부에서 @Autowired는 아무 기능도 동작하지 않는다

//    TODO : 생성자 주입
/**     생성자를 통해서 의존 관계를 주입
 *      불변, 필수 의존 관계에 사용
 *      생성자 호출시점에 딱 1번만 호출되는 것이 보장
 *      주입하는것을 실수 할 수 있으니 final을 붙여서 넣지 않았을때 컴파일 오류가 발생하도록 한다
 *
 *      ※ 생성자가 하나만 있으면 @Autowired 생략 가능!!
 */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

//    @Autowired & 하나만 있으면 없어도 가능
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

//    TODO : 수정자 주입(Setter 주입)
/**     선택, 변경 가능성이 있는 의존관계에 사용
 *      자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법
 *
 *      참고 : @Autowired의 기본 동작은 주입할 대상이 없으면 오류가 발생한다. 주입할 대상이 없어도 동작하게 하려면
 *             @Autowired(required = flase)로 지정하면 된다.
 */
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }


//    TODO : 필드 주입
    /**     단점 : 임의의 테스트 코드를 작성 할 수 없음
     *      예시 : OrderServiceTest.java → fileInjectionTest
     *      코드가 간결해서 많은 개발자들을 유혹하지만 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적 단점이 존재
     *      DI 프레임워크가 없으면 아무것도 할 수 없으므로 현재 필드 주입은 사용 안함
     *
     *      ※ 어플리케이션의 실제 코드와 관계 없는 테스트 코드에서 사용
     *          !! 테스트 때 스프링 컨테이너를 띄워서 테스트한다면 테스트 할 때만 사용하는 것이므로 필드 주입 사용 가능
     *
     *      ※ 스프링 설정을 목적으로 하는 @Configuration 같은 곳에서만 특별한 용도로 사용
     *          !! @Configuration의 클래스에서 수동 빈 등록시 사용 가능 (다른 좋은 방법이 많아서 추천하지 않음)
     *             AutoAppConfig.java에 방법 1, 방법 2 설정함
     */
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;


//  TODO : 일반 메서드 주입
/**     일반 메서드를 통해서 주입
 *      특징
 *          1. 한번에 여러 필드를 주입 받을 수 있다.
 *          2. 일반적으로 잘 사용하지 않는다.
 *      ※ 일반적으로 생성자 주입이나 수정자 주입에서 다 생성하여 사용하기 때문에 사용할 일이 거의 없다
 */
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;//
//    @Autowired
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }


    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
