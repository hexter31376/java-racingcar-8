package racingcar.presentation;

import racingcar.application.port.in.CarRacingUseCase;
import racingcar.presentation.assembler.RacingResponseAssembler;
import racingcar.presentation.dto.request.StartRaceRequest;
import racingcar.presentation.dto.response.RacingStatusResponse;
import racingcar.presentation.dto.response.WinnerResponse;
import racingcar.view.input.InputView;
import racingcar.view.output.OutputView;

public class CarRacingController {
    private final InputView inputView;
    private final OutputView outputView;
    private final CarRacingUseCase carRacing;
    private final RacingResponseAssembler assembler;

    public CarRacingController(InputView inputView,
                               OutputView outputView,
                               CarRacingUseCase carRacing,
                               RacingResponseAssembler assembler) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.carRacing = carRacing;
        this.assembler= assembler;
    }

    public void run() {
        // 1. 레이싱 시작 요청 날리기
        StartRaceRequest startRaceRequest = inputView.readline();
        carRacing.start(startRaceRequest);

        // 2. 라운드별 경기 결과 출력
        while (carRacing.hasNextRound()) {
            RacingStatusResponse racingStatus = assembler.assembleRacingStatus(carRacing.getRoundResult());
            outputView.printRacingStatus(racingStatus);
        }

        // 3. 최종 우승자 발표
        WinnerResponse winners = assembler.assembleWinners(carRacing.getFinalWinners());
        outputView.printWinners(winners);
    }
}
