package racingcar.application.infrastructure.repository;

import racingcar.domain.car.Cars;
import racingcar.domain.game.GameState;

// 게임 하나만 들고 있을수 있게 설계 간소화
public class GameRepository {
    private GameSnap gameSnap;

    public void save (Cars cars, GameState gameState) {
        GameSnap gameSnap = new GameSnap(cars, gameState);
    }

    public GameSnap getGameSnap() {
        return gameSnap;
    }
}
