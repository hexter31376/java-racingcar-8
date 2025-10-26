package racingcar.domain.car;

public class Car {

    private final String name;
    private int position;

    private Car(String name) {
        this.name = name;
        this.position = 0;
    }

    public static Car validatedFrom(String name) {
        validate(name);
        return new Car(name);
    }

    public static void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("자동차 이름은 비어 있을 수 없습니다. : " + name);
        }
        if (name.length() >= 5) {
            throw new IllegalArgumentException("자동차 이름은 5자 이하만 가능합니다. : " + name);
        }
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void move() {
        this.position++;
    }
}
