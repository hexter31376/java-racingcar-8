package racingcar.application.infrastructure.config;

import racingcar.application.infrastructure.repository.GameRepository;
import racingcar.application.service.CarRacingService;
import racingcar.application.service.InputParser;
import racingcar.application.service.RandomGenerator;
import racingcar.presentation.CarRacingController;
import racingcar.view.input.InputView;
import racingcar.view.output.OutputView;

public class AppConfig {
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();

    private final InputParser inputParser = new InputParser();
    private final RandomGenerator randomGenerator = new RandomGenerator();

    private final GameRepository gameRepository = new GameRepository();

    private final CarRacingService carRacingService = new CarRacingService(inputParser, randomGenerator, gameRepository);

    private final CarRacingController carRacingController = new CarRacingController(inputView, outputView, carRacingService);

    public CarRacingController getRacingCarController() {
        return carRacingController;
    }
}
