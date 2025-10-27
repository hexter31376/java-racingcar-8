// java
package racingcar.domain.car;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CarTest {

    @Test
    void from_validName_shouldCreateCarWithInitialPositionZero() {
        // 정상 이름으로 생성 시 이름과 초기 위치(0)를 검증
        Car car = Car.from("pobi");

        assertThat(car.getName()).isEqualTo("pobi");
        assertThat(car.getPosition()).isZero();
    }

    @Test
    void move_shouldIncreasePositionByOne() {
        // move 호출 시 position이 1 증가하는지 검증
        Car car = Car.from("pobi");
        car.move();

        assertThat(car.getPosition()).isEqualTo(1);
    }

    @Test
    void from_nullOrBlankName_shouldThrowIllegalArgumentException() {
        // null 입력 시 예외 발생 검증
        assertThatThrownBy(() -> Car.from(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비어");

        // 빈 문자열(공백 포함) 입력 시 예외 발생 검증
        assertThatThrownBy(() -> Car.from("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비어");
    }

    @Test
    void from_nameLongerThanFive_shouldThrowIllegalArgumentException() {
        // 6자 이상 이름은 허용되지 않음
        assertThatThrownBy(() -> Car.from("abcdef"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("5자 이하");
    }

    @Test
    void from_nameExactlyFive_shouldBeAllowed() {
        // 정확히 5자면 생성 가능
        Car car = Car.from("abcde");

        assertThat(car.getName()).isEqualTo("abcde");
        assertThat(car.getPosition()).isZero();
    }
}