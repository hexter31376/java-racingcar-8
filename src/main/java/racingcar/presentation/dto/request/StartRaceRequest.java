package racingcar.presentation.dto.request;

public record StartRaceRequest(
    String carNames,
    Integer tryCount
) {
}
