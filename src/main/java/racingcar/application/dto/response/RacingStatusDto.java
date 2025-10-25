package racingcar.application.dto.response;

import java.util.List;

public record RacingStatusDto(
        List<CarStatusDto> carStatuses
) {
}
