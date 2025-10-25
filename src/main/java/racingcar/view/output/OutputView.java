package racingcar.view.output;

import racingcar.presentation.dto.response.CarStatusResponse;
import racingcar.presentation.dto.response.RacingStatusResponse;
import racingcar.view.output.enums.OutputTemplate;

import java.util.List;

public class OutputView {

    public void printResultTemplate() {
        System.out.println(OutputTemplate.OUTPUT_TITLE_MESSAGE);
    }

    // 현재 달리는 자동차들의 상태를 출력하는 메서드 ex) "car1 : ---"
    public void printRacingStatus(RacingStatusResponse racingStatusResponse) {
        List<CarStatusResponse> carStatuses = racingStatusResponse.carStatuses();

        for (CarStatusResponse carStatus : carStatuses) {
            String carStatusOutput = carStatus.carName() +
                    " : " +
                    "-".repeat(carStatus.position());
            System.out.println(carStatusOutput);
        }
    }

    public void printWinners(String winners) {
        System.out.println(OutputTemplate.OUTPUT_WINNER_MESSAGE + winners);
    }
}