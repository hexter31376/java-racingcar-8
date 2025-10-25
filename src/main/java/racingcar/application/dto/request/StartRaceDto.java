package racingcar.application.dto.request;

public record StartRaceDto (
    String carNames,
    int tryCount
) {
}
