package racingcar.config;

import racingcar.application.service.CarRacingService;
import racingcar.presentation.CarRacingController;
import racingcar.view.input.InputView;
import racingcar.view.output.OutputView;

public class AppConfig {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final CarRacingService carRacingService = new CarRacingService();

    private final CarRacingController carRacingController = new CarRacingController(inputView, outputView, carRacingService);

    public CarRacingController getRacingCarController() {
        return carRacingController;
    }
}
