package racingcar.application.service;

import java.util.Arrays;
import java.util.List;

public class InputParser {

    public List<String> parseInput(String input) {
        validateInput(input);
        // , 앞뒤의 공백을 무시하고 출력
        return Arrays.stream(input.split("\\s*,\\s*"))
                .map(carName -> {
                    validateCarNameExist(carName);
                    return carName;
                })
                .toList();
    }

    private void validateCarNameExist(String carName) {
        if (carName.isBlank()) {
            throw new IllegalArgumentException("올바르지 않은 입력입니다, 차 항목이 비어있습니다. : ");
        }
    }

    private void validateInput(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("입력값이 비어있습니다." + input);
        }
    }
}
