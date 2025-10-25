package racingcar.application.dto.request;

public record StartRaceDto (
    String carNames,
    Integer tryCount
) {
}
