package racingcar.view.output;

import racingcar.application.dto.response.CarStatusResponse;
import racingcar.application.dto.response.RacingStatusResponse;
import racingcar.application.dto.response.WinnerResponse;
import racingcar.view.output.enums.OutputTemplate;

import java.util.List;

public class OutputView {

    public void printResultTemplate() {
        System.out.println(OutputTemplate.OUTPUT_TITLE_MESSAGE.getMessage());
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
        System.out.println("\n");
    }

    // 최종 우승자를 출력하는 메서드 ex) "최종 우승자: car1, car2"
    public void printWinners(WinnerResponse winnerResponse) {
        System.out.print(OutputTemplate.OUTPUT_WINNER_MESSAGE.getMessage());
        System.out.println(String.join(", ", winnerResponse.winners()));
    }
}