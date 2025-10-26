package racingcar.application.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InputParser {

    public List<String> parseInput(String input) {
        // 1. null 및 빈 문자열 체크
        validateInput(input);
        // 2. ,앞뒤의 공백을 무시하고 삽입함과 동시에 중복 여부 검사
        Set<String> seen = new HashSet<>(); // 중복 여부 체크를 위한 해시셋
        return Arrays.stream(input.split("\\s*,\\s*"))
                .map(carName -> {
                    validateCarNameAllExist(carName); // 콤마를 기준으로 차 이름이 전부 존재하는지
                    validateCarNameDuplicated(carName, seen); // 차 이름 중복 체크
                    return carName;
                })
                .toList();
    }

    private void validateCarNameAllExist(String carName) {
        if (carName.isBlank()) {
            throw new IllegalArgumentException("올바르지 않은 입력입니다, 차 항목이 비어있습니다.");
        }
    }

    private void validateCarNameDuplicated(String carName, Set<String> seen) {
        if (seen.contains(carName)) {
            throw new IllegalArgumentException("자동차의 이름은 중복될 수 없습니다.");
        }
    }

    private void validateInput(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("입력값이 비어있습니다." + input);
        }
    }
}
