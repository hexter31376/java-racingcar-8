package racingcar.application.infrastructure.repository;

import racingcar.domain.car.Cars;
import racingcar.domain.game.GameState;

// Cars와 GameState의 스냅샷 저장
public record GameSnap(
        Cars cars,
        GameState gameState
) {
}
