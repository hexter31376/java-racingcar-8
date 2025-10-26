package racingcar.application.dto.request;

public record StartRaceRequest(
    String carNames,
    Integer tryCount
) {
}
