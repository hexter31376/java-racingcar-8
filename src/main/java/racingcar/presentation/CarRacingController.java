package racingcar.presentation;

import racingcar.application.service.CarRacingService;
import racingcar.application.dto.request.StartRaceRequest;
import racingcar.application.dto.response.RacingStatusResponse;
import racingcar.application.dto.response.WinnerResponse;
import racingcar.view.input.InputView;
import racingcar.view.output.OutputView;

public class CarRacingController {
    private final InputView inputView;
    private final OutputView outputView;
    private final CarRacingService carRacing;

    public CarRacingController(InputView inputView,
                               OutputView outputView,
                               CarRacingService carRacing) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.carRacing = carRacing;
    }

    public void run() {
        // 1. 레이싱 시작 요청 날리기
        StartRaceRequest startRaceRequest = inputView.readline();
        carRacing.start(startRaceRequest);

        // 결과 출력 시작 메시지
        outputView.printResultTemplate();

        // 2. 라운드별 경기 결과 출력
        while (carRacing.hasNextRound()) {
            RacingStatusResponse racingStatus = carRacing.getRoundResult();
            outputView.printRacingStatus(racingStatus);
        }

        // 3. 최종 우승자 발표
        WinnerResponse winners = carRacing.getFinalWinners();
        outputView.printWinners(winners);
    }
}
