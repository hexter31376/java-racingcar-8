package racingcar.application.dto.output;

public record CarStatusResponse(
        String carName,
        Integer position
) implements CarStatusResponse {
}
