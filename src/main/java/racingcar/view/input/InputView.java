package racingcar.view.input;

import camp.nextstep.edu.missionutils.Console;
import racingcar.application.dto.request.StartRaceRequest;
import racingcar.view.input.enums.InputTemplate;

public class InputView {

    public StartRaceRequest readline() {

        System.out.println(InputTemplate.INPUT_CAR_NAMES_MESSAGE.getMessage());
        String carList = Console.readLine();

        System.out.println(InputTemplate.INPUT_TRY_COUNT_MESSAGE.getMessage());
        String tryCount = Console.readLine();

        return new StartRaceRequest(carList, Integer.parseInt(tryCount));
    }
}
