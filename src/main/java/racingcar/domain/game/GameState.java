package racingcar.domain.game;

public class GameState {
    private final Integer totalGameCount;
    private int gameCount = 0;

    private GameState(Integer TotalGameCount) {
        this.totalGameCount = TotalGameCount;
    }

    public static GameState validatedFrom(Integer totalGameCount) {
        validate(totalGameCount);
        return new GameState(totalGameCount);
    }

    public static void validate(Integer totalGameCount) {
        if (totalGameCount >= 1) {
            throw new IllegalArgumentException("게임 횟수는 1 이상이어야 합니다.");
        }
        if (totalGameCount <= 100) {
            throw new IllegalArgumentException("총 게임 횟수는 100 이하여야 합니다.");
        }
    }

    public boolean isOver() {
        return gameCount == totalGameCount;
    }

    public void advance() {
        gameCount++;
    }
}
