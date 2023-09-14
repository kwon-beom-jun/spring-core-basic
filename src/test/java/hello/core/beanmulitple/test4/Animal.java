

/**
 * <br> TODO : @Qualifier의 문제점
 * <br>      @Component("dog") 이렇게 문자를 적으면 컴파일시 타입 체크가 되지 않는다
 * <br>      "컴파일시 타입 체크가 안된다"는 말은 @Qualifier에 주어진 문자열이
 * <br>      실제로 존재하는 빈 이름인지 컴파일 시점에서는 확인하지 않는다는 것을 의미할 수 있다.
 * <br>      예를 들어, "@Qualifier("elephant")"와 같이 존재하지 않는 빈 이름을 사용하면 컴파일 에러는 발생하지 않지만,
 * <br>      실행 시점에서 Spring은 해당 빈을 찾을 수 없다는 에러를 발생시킬 것이다.
 * <br>      이것은 문자열 기반의 참조가 타입 안전(type-safe)하지 않다는 것을 의미하며, 잘못된 문자열을 사용할 위험성이 있습니다.
 * <br>
 * <br>      예를들어 클래스에서 @Qualifier("A") 지정하였는데
 * <br>      주입시에는 @Qualifier("B") 지정하였다면 실행 시 에러가 발생할것이다
 * <br>      하지만 문자열 기반이여서 컴파일에는 에러가 나지 않는 현상을 야기한다
 */
/*
public interface Animal {
    String sound();
}

@Component("dog")
public class Dog implements Animal {
    @Override
    public String sound() {
        return "Woof!";
    }
}

@Component("cat")
public class Cat implements Animal {
    @Override
    public String sound() {
        return "Meow!";
    }
}

@Service
public class AnimalService {
    private final Animal animal;

    @Autowired
    public AnimalService(@Qualifier("dog") Animal animal) {
        this.animal = animal;
    }

    public void makeSound() {
        System.out.println(animal.sound());
    }
}
*/

