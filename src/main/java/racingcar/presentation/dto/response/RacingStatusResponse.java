package racingcar.presentation.dto.response;

import java.util.List;

public record RacingStatusResponse(
        List<CarStatusResponse> carStatuses
) {
}
