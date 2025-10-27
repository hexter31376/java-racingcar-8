// 파일: `src/test/java/racingcar/presentation/CarRacingControllerTest.java`
package racingcar.presentation;

import org.junit.jupiter.api.Test;
import racingcar.application.service.CarRacingService;
import racingcar.application.dto.request.StartRaceRequest;
import racingcar.application.dto.response.RacingStatusResponse;
import racingcar.application.dto.response.WinnerResponse;
import racingcar.view.input.InputView;
import racingcar.view.output.OutputView;

import static org.assertj.core.api.Assertions.*;

class CarRacingControllerTest {

    @Test
    void run_invokes_start_prints_template_prints_each_round_and_final_winners() {
        // arrange
        StartRaceRequest expectedRequest = new StartRaceRequest("a,b", 3);
        StubInputView input = new StubInputView(expectedRequest);
        StubOutputView output = new StubOutputView();
        SpyCarRacingService service = new SpyCarRacingService(new boolean[]{ true, true, false });

        CarRacingController controller = new CarRacingController(input, output, service);

        // act
        controller.run();

        // assert
        assertThat(service.startCalled).isTrue();
        assertThat(service.receivedRequest).isSameAs(expectedRequest);

        assertThat(output.resultTemplatePrinted).isTrue();
        assertThat(output.racingStatusCount).isEqualTo(2);
        assertThat(service.roundResultCalls).isEqualTo(2);
        assertThat(output.winnersPrinted).isTrue();
    }

    // --- 스텁/스파이 구현체들 ---

    // InputView가 클래스인 경우를 고려해 간단히 확장하여 readline 오버라이드
    private static class StubInputView extends InputView {
        private final StartRaceRequest req;

        StubInputView(StartRaceRequest req) {
            super(); // 기존 InputView의 기본 동작(콘솔 출력 등)이 있더라도 테스트 흐름상 허용
            this.req = req;
        }

        @Override
        public StartRaceRequest readline() {
            return req;
        }
    }

    private static class StubOutputView extends OutputView {
        boolean resultTemplatePrinted = false;
        int racingStatusCount = 0;
        boolean winnersPrinted = false;

        @Override
        public void printResultTemplate() {
            resultTemplatePrinted = true;
        }

        @Override
        public void printRacingStatus(RacingStatusResponse racingStatusResponse) {
            racingStatusCount++;
        }

        @Override
        public void printWinners(WinnerResponse winners) {
            winnersPrinted = true;
        }
    }

    // CarRacingService가 클래스인 상황을 고려한 테스트용 스파이
    private static class SpyCarRacingService extends CarRacingService {
        boolean startCalled = false;
        StartRaceRequest receivedRequest = null;
        int roundResultCalls = 0;
        private final boolean[] hasNextSequence;
        private int idx = 0;

        SpyCarRacingService(boolean[] hasNextSequence) {
            super(null, null, null); // 실제 의존성은 사용하지 않음. 필요시 더미 인스턴스로 변경 가능
            this.hasNextSequence = hasNextSequence;
        }

        @Override
        public void start(StartRaceRequest request) {
            startCalled = true;
            receivedRequest = request;
        }

        @Override
        public boolean hasNextRound() {
            if (idx >= hasNextSequence.length) return false;
            return hasNextSequence[idx++];
        }

        @Override
        public RacingStatusResponse getRoundResult() {
            roundResultCalls++;
            return null;
        }

        @Override
        public WinnerResponse getFinalWinners() {
            return null;
        }
    }
}