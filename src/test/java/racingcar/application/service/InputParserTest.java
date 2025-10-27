// java
// 파일: src/test/java/racingcar/application/service/InputParserTest.java
package racingcar.application.service;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class InputParserTest {

    @Test
    void parse_valid_shouldReturnParsedList() {
        InputParser parser = new InputParser();

        List<String> result = parser.parse("a , b");

        assertThat(result).containsExactly("a", "b");
    }

    @Test
    void parse_withEmptyItem_shouldThrowIllegalArgumentException() {
        InputParser parser = new InputParser();

        assertThatThrownBy(() -> parser.parse("a,,b"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비어있습니다");
    }

    @Test
    void parse_duplicateNames_shouldThrowIllegalArgumentException() {
        InputParser parser = new InputParser();

        assertThatThrownBy(() -> parser.parse("a,b,a"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("중복");
    }

    @Test
    void parseInput_blank_shouldThrowIllegalArgumentException() {
        InputParser parser = new InputParser();

        assertThatThrownBy(() -> parser.parse("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("입력값이 비어있습니다");
    }

    @Test
    void parseInput_null_shouldThrowNullPointerException() {
        InputParser parser = new InputParser();

        assertThatThrownBy(() -> parser.parse(null))
                .isInstanceOf(NullPointerException.class);
    }
}
