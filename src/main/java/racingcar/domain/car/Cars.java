package racingcar.domain.car;

import java.util.HashMap;
import java.util.Map;

public class Cars {
    private final Map<String, Car> values;

    private Cars(Map<String, Car> value) {
        validate(value);
        this.values = value;
    }

    public static Cars validatedOf(Map<String, Car> cars) {
        return new Cars(cars);
    }

    public void validate(Map<String, Car> value) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("자동차는 한 대 이상 있어야 합니다.");
        }
    }

    public Map<String, Car> getValues() {
        return new HashMap<>(values);
    }

    public void moveCar(String name) {
        Car car = values.get(name);
        validate(car.getName());
        car.move();
    }

    public void validate(String name) {
        if (values.containsKey(name)) {
            throw new IllegalArgumentException("자동차 이름은 중복될 수 없습니다. : " + name);
        }
    }
}
