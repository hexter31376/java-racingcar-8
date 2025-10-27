// java
package racingcar.infrastructure.repository;

import org.junit.jupiter.api.Test;
import racingcar.domain.car.Car;
import racingcar.domain.car.Cars;
import racingcar.domain.game.GameState;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class GameRepositoryTest {

    @Test
    void initial_snapshot_shouldBeNull() {
        GameRepository repo = new GameRepository();

        assertThat(repo.getGameSnap()).isNull();
    }

    @Test
    void save_shouldStoreGameSnap() {
        GameRepository repo = new GameRepository();

        Map<String, Car> map = new LinkedHashMap<>();
        map.put("a", Car.from("a"));
        Cars cars = Cars.of(map);
        GameState gs = GameState.from(3);

        repo.save(cars, gs);

        assertThat(repo.getGameSnap()).isNotNull();
    }

    @Test
    void save_twice_shouldReplacePreviousSnapshot() {
        GameRepository repo = new GameRepository();

        Map<String, Car> map1 = new LinkedHashMap<>();
        map1.put("a", Car.from("a"));
        Cars cars1 = Cars.of(map1);
        GameState gs1 = GameState.from(1);

        repo.save(cars1, gs1);
        Object firstSnap = repo.getGameSnap();

        Map<String, Car> map2 = new LinkedHashMap<>();
        map2.put("b", Car.from("b"));
        Cars cars2 = Cars.of(map2);
        GameState gs2 = GameState.from(2);

        repo.save(cars2, gs2);
        Object secondSnap = repo.getGameSnap();

        assertThat(secondSnap).isNotNull();
        assertThat(secondSnap).isNotSameAs(firstSnap);
    }
}