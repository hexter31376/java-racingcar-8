package racingcar.domain.car;

import java.util.HashMap;
import java.util.Map;

public class Cars {
    private final Map<String, Car> cars;

    private Cars(Map<String, Car> cars) {
        this.cars = cars;
    }

    public static Cars validatedOf(Map<String, Car> cars) {
        validate(cars);
        return new Cars(cars);
    }


    public static void validate(Map<String, Car> cars) {
        if (cars == null || cars.isEmpty()) {
            throw new IllegalArgumentException("자동차는 한 대 이상 있어야 합니다.");
        }
    }

    public Map<String, Car> getValues() {
        return cars;
    }

    public void moveCar(String name) {
        Car car = cars.get(name);
        car.move();
    }
}
