package hello.core.singleton;

/**
 * <br>  TODO : 싱글톤 주의사항
 * <br>       필드 대신에 자바에서 공유되지 않은 지역변수, 파라미터, ThreadLocal 등을 사용해야한다.
 */
public class StatefulService {

    private int price;

    public void order(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        this.price = price; // 여기가 문제
    }

    /**
     * <br>  TODO : 싱글톤 주의 예시
     * <br>       private int price 필드를 지우고 메소드에서 반환
     * <br>       StatefulServiceTest에서 int값을 받아서 사용
     */
    public int order2(String name, int price) {
        System.out.println("name = " + name + " price = " + price);
        return price;
    }

    public int getPrice() {
        return price;
    }
}
