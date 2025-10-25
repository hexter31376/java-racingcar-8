package racingcar.view.input;

import camp.nextstep.edu.missionutils.Console;
import racingcar.application.dto.input.ViewRequest;
import racingcar.view.input.enums.InputTemplate;

public class InputView {

    public ViewRequest readline() {

        System.out.println(InputTemplate.INPUT_CAR_NAMES_MESSAGE.getMessage());
        String carList = Console.readLine();

        System.out.println(InputTemplate.INPUT_TRY_COUNT_MESSAGE.getMessage());
        String tryCount = Console.readLine();

        return new ViewRequest(carList, Integer.parseInt(tryCount));
    }
}
