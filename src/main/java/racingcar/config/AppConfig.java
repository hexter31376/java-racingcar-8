package racingcar.config;

import racingcar.application.port.in.CarRacingUseCase;
import racingcar.application.service.CarRacingService;
import racingcar.presentation.CarRacingController;
import racingcar.presentation.assembler.RacingResponseAssembler;
import racingcar.view.input.InputView;
import racingcar.view.output.OutputView;

public class AppConfig {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final CarRacingUseCase carRacingService = new CarRacingService();
    private final RacingResponseAssembler racingResponseAssembler = new RacingResponseAssembler();

    private final CarRacingController carRacingController = new CarRacingController(inputView, outputView, carRacingService, racingResponseAssembler);

    public CarRacingController getRacingCarController() {
        return carRacingController;
    }
}
