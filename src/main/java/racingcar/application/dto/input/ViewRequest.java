package racingcar.application.dto.input;

public record ViewRequest(
    String carNames,
    int tryCount
) {
}
