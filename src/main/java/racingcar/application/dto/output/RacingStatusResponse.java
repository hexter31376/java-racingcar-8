package racingcar.application.dto.output;

import java.util.List;

public record RacingStatusResponse(
        List<CarStatusResponse> carStatuses
) {
}
