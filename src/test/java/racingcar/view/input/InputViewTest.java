package racingcar.view.input;

import org.junit.jupiter.api.*;
import racingcar.application.dto.request.StartRaceRequest;

import java.io.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputViewTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream out;

    // 입력 가로채기를 위한 스트림 설정
    @BeforeEach
    void setUpStreams() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    // 원래 스트림 복원
    @AfterEach
    void cleanUpStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        camp.nextstep.edu.missionutils.Console.close();
    }

    // 정상 입력에 대한 테스트
    @Test
    void caseNormalInput_shouldParseAndPrintPrompts() {
        // given
        System.setIn(new ByteArrayInputStream("car1,car2,car3\n6\n".getBytes()));
        InputView inputView = new InputView();

        // when
        StartRaceRequest request = inputView.readline();

        // then: 출력 포맷
        String printed = out.toString();
        assertThat(printed).contains("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        assertThat(printed).contains("시도할 회수는 몇 회인가요?");

        // then: DTO 필드 (이름/타입에 맞춰 조정)
        // StartRaceRequest가 carList(String), tryCount(int)라고 가정
        assertThat(request.carNames()).isEqualTo("car1,car2,car3");
        assertThat(request.tryCount()).isEqualTo(6);
    }
}
