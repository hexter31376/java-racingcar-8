package racingcar.domain.game;

public final class GameState {
    private final int totalRounds;   // 총 게임 횟수(1~100)
    private int round = 0;           // 현재 진행 라운드 수

    private GameState(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public static GameState from(int totalRounds) {
        validate(totalRounds);
        return new GameState(totalRounds);
    }

    private static void validate(int totalRounds) {
        if (totalRounds < 1) {
            throw new IllegalArgumentException("게임 횟수는 1 이상이어야 합니다.");
        }
        if (totalRounds > 100) {
            throw new IllegalArgumentException("총 게임 횟수는 100 이하여야 합니다.");
        }
    }

    public boolean isOver() {
        return round >= totalRounds;
    }

    public void advance() {
        round++;
    }

    // 필요시 게터
    public int totalRounds() { return totalRounds; }
    public int currentRound() { return round; }
}
