package hello.core.order;

/**
 * 주문 후 결과를 반환되는 객체
 *  - 실제로는 주문 데이터를 DB에 저장
 */
public interface OrderService {

    Order createOrder(Long memberId, String itemName, int itemPrice);

}
