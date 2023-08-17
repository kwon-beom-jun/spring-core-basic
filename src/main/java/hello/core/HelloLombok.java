package hello.core;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {

    private String str;
    private int num;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setStr("TEST");
        helloLombok.setNum(1);
        System.out.println(helloLombok.toString());
    }

}
