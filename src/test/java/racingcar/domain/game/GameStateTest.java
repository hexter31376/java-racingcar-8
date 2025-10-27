// java
package racingcar.domain.game;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameStateTest {

    // 정상 범위로 생성 시 초기 상태 검증
    @Test
    void from_validRange_shouldInitializeCorrectly() {
        GameState gs = GameState.from(5);

        assertThat(gs.totalRounds()).isEqualTo(5);
        assertThat(gs.currentRound()).isZero();
        assertThat(gs.isOver()).isFalse();
    }

    // 1 미만 입력 시 예외 발생 검증
    @Test
    void from_lessThanOne_shouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> GameState.from(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("1 이상");
    }

    // 100 초과 입력 시 예외 발생 검증
    @Test
    void from_greaterThanHundred_shouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> GameState.from(101))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("100");
    }

    // advance 호출에 따른 currentRound 증가와 isOver 판정 검증
    @Test
    void advance_shouldIncrementRound_and_isOverWhenReachedTotal() {
        GameState gs = GameState.from(3);

        gs.advance();
        assertThat(gs.currentRound()).isEqualTo(1);
        assertThat(gs.isOver()).isFalse();

        gs.advance();
        assertThat(gs.currentRound()).isEqualTo(2);
        assertThat(gs.isOver()).isFalse();

        gs.advance();
        assertThat(gs.currentRound()).isEqualTo(3);
        assertThat(gs.isOver()).isTrue();

        // 추가 진행 시에도 currentRound는 증가하고 isOver는 계속 true
        gs.advance();
        assertThat(gs.currentRound()).isEqualTo(4);
        assertThat(gs.isOver()).isTrue();
    }

    // totalRounds가 1일 때의 경계 동작 검증
    @Test
    void singleRound_shouldBeOverAfterOneAdvance() {
        GameState gs = GameState.from(1);

        assertThat(gs.isOver()).isFalse();
        gs.advance();
        assertThat(gs.currentRound()).isEqualTo(1);
        assertThat(gs.isOver()).isTrue();
    }
}