package racingcar.application.assembler;

import racingcar.application.dto.request.StartRaceDto;
import racingcar.presentation.dto.request.StartRaceRequest;

public class StartRaceRequestAssembler {
    StartRaceDto startRaceDtoAssemble(StartRaceRequest startRaceRequest) {
        return new StartRaceDto(
                startRaceRequest.carNames(),
                startRaceRequest.tryCount()
        );
    }
}
