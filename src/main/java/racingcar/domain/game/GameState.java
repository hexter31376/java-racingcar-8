package racingcar.domain.game;

public class GameState {
    private final Integer TotalGameCount;
    private int gameCount = 0;

    private GameState(Integer TotalGameCount) {
        this.TotalGameCount = TotalGameCount;
    }

    public Integer validatedFrom(Integer TotalGameCount) {
        validate(TotalGameCount);
        return TotalGameCount;
    }

    public boolean isOver() {
        return gameCount == TotalGameCount;
    }

    public void advance() {
        gameCount++;
    }

    public void validate(Integer totalGameCount) {
        if (totalGameCount >= 1) {
            throw new IllegalArgumentException("게임 횟수는 1 이상이어야 합니다.");
        }
        if (totalGameCount <= 100) {
            throw new IllegalArgumentException("총 게임 횟수는 100 이하여야 합니다.");
        }
    }
}
