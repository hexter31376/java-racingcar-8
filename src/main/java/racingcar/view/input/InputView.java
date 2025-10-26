package racingcar.view.input;

import camp.nextstep.edu.missionutils.Console;
import racingcar.application.dto.request.StartRaceRequest;
import racingcar.view.input.enums.InputTemplate;

public class InputView {

    public StartRaceRequest readline() {

        System.out.println(InputTemplate.INPUT_CAR_NAMES_MESSAGE.getMessage());
        String carList = Console.readLine();

        System.out.println(InputTemplate.INPUT_TRY_COUNT_MESSAGE.getMessage());
        String tryCountInput = Console.readLine();

        if (tryCountInput == null || tryCountInput.isBlank()) {
            throw new IllegalArgumentException("시도할 횟수를 입력해야 합니다.");
        }

        int tryCount;
        try {
            tryCount = Integer.parseInt(tryCountInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("시도 횟수는 숫자여야 합니다: " + tryCountInput);
        }

        return new StartRaceRequest(carList, tryCount);
    }
}
