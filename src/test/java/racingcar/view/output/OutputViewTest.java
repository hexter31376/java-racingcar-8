// java
package racingcar.view.output;

import org.junit.jupiter.api.Test;
import racingcar.application.dto.response.CarStatusResponse;
import racingcar.application.dto.response.RacingStatusResponse;
import racingcar.application.dto.response.WinnerResponse;
import racingcar.view.output.enums.OutputTemplate;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class OutputViewTest {

    @Test
    void printResultTemplate_printsTitleMessage() {
        OutputView view = new OutputView();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            view.printResultTemplate();

            String printed = out.toString();
            assertThat(printed).contains(OutputTemplate.OUTPUT_TITLE_MESSAGE.getMessage());
        } finally {
            System.setOut(original);
        }
    }

    @Test
    void printRacingStatus_printsEachCarStatusWithHyphens() {
        // DTO 생성(일반적인 생성자/레코드 형태를 가정)
        CarStatusResponse car1 = new CarStatusResponse("pobi", 3);
        CarStatusResponse car2 = new CarStatusResponse("crong", 1);
        RacingStatusResponse racingStatus = new RacingStatusResponse(List.of(car1, car2));

        OutputView view = new OutputView();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            view.printRacingStatus(racingStatus);

            String printed = out.toString();
            assertThat(printed).contains("pobi : ---");
            assertThat(printed).contains("crong : -");
            assertThat(printed).endsWith(System.lineSeparator() + System.lineSeparator());
        } finally {
            System.setOut(original);
        }
    }

    @Test
    void printWinners_printsWinnerMessageAndNamesJoinedByComma() {
        WinnerResponse winnerResponse = new WinnerResponse(List.of("a", "b"));

        OutputView view = new OutputView();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(out));
        try {
            view.printWinners(winnerResponse);

            String printed = out.toString();
            assertThat(printed).contains(OutputTemplate.OUTPUT_WINNER_MESSAGE.getMessage());
            assertThat(printed).contains("a, b");
            assertThat(printed).endsWith(System.lineSeparator());
        } finally {
            System.setOut(original);
        }
    }
}