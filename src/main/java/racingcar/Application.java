package racingcar;

import racingcar.application.infrastructure.config.AppConfig;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        appConfig.getRacingCarController().run();
    }
}
