package racingcar.view.input;

import camp.nextstep.edu.missionutils.Console;
import org.junit.jupiter.api.*;
import racingcar.application.dto.request.StartRaceRequest;

import java.io.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputViewTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;

    @BeforeEach
    void setUpStreams() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void cleanUpStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        Console.close();
    }

    @Test
    void caseNormalInput_shouldParseAndPrintPrompts() {
        System.setIn(new ByteArrayInputStream("car1,car2,car3\n6\n".getBytes()));
        InputView inputView = new InputView();

        StartRaceRequest request = inputView.readline();

        String printed = out.toString();
        assertThat(printed).contains("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)\n");
        assertThat(printed).contains("시도할 횟수는 몇 회인가요?\n");

        assertThat(request.carNames()).isEqualTo("car1,car2,car3");
        assertThat(request.tryCount()).isEqualTo(6);
    }

    @Test
    void caseEmptyTryCount_shouldThrow() {
        System.setIn(new ByteArrayInputStream("car1,car2\n\n".getBytes()));
        InputView inputView = new InputView();

        assertThrows(IllegalArgumentException.class, inputView::readline);
    }

    @Test
    void caseNonNumericTryCount_shouldThrow() {
        System.setIn(new ByteArrayInputStream("car1,car2\nabc\n".getBytes()));
        InputView inputView = new InputView();

        assertThrows(IllegalArgumentException.class, inputView::readline);
    }
}