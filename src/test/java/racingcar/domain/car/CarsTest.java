package racingcar.domain.car;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class CarsTest {

    @Test
    void of_withValidMap_shouldCreateCarsAndPreserveOrderAndInitialPositions() {
        Map<String, Car> map = new LinkedHashMap<>();
        map.put("a", Car.from("a"));
        map.put("b", Car.from("b"));

        Cars cars = Cars.of(map);

        assertThat(cars.getValues().keySet()).containsExactly("a", "b");
        assertThat(cars.getValues().values()).allMatch(car -> car.getPosition() == 0);
    }

    @Test
    void of_withNullOrEmpty_shouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> Cars.of(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("자동차는 한 대 이상 있어야 합니다.");

        assertThatThrownBy(() -> Cars.of(new LinkedHashMap<>()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("자동차는 한 대 이상 있어야 합니다.");
    }

    @Test
    void moveCar_shouldMoveOnlyTargetCar() {
        Map<String, Car> map = new LinkedHashMap<>();
        map.put("a", Car.from("a"));
        map.put("b", Car.from("b"));

        Cars cars = Cars.of(map);

        cars.moveCar("b");

        assertThat(cars.getValues().get("a").getPosition()).isEqualTo(0);
        assertThat(cars.getValues().get("b").getPosition()).isEqualTo(1);
    }

    @Test
    void moveCar_withUnknownName_shouldThrowNullPointerException() {
        Map<String, Car> map = new LinkedHashMap<>();
        map.put("a", Car.from("a"));

        Cars cars = Cars.of(map);

        assertThatThrownBy(() -> cars.moveCar("unknown"))
                .isInstanceOf(NullPointerException.class);
    }
}
