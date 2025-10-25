package racingcar.presentation.assembler;

import racingcar.application.dto.response.CarStatusDto;
import racingcar.application.dto.response.RacingStatusDto;
import racingcar.application.dto.response.WinnerDto;
import racingcar.presentation.dto.response.CarStatusResponse;
import racingcar.presentation.dto.response.RacingStatusResponse;
import racingcar.presentation.dto.response.WinnerResponse;

// 리스폰스 어셈블러: 애플리케이션 레이어에서 전달된 DTO를 프레젠테이션 레이어의 Response로 변환
public class RacingResponseAssembler {

    public CarStatusResponse assembleCarStatus (CarStatusDto carStatusDto) {
        return new CarStatusResponse(
                carStatusDto.carName(),
                carStatusDto.position()
        );
    }

    public RacingStatusResponse assembleRacingStatus(RacingStatusDto racingStatusDto) {
        var carStatusResponses = racingStatusDto.carStatuses().stream()
                .map(this::assembleCarStatus)
                .toList();
        return new RacingStatusResponse(carStatusResponses);
    }

    public WinnerResponse assembleWinners(WinnerDto winnerDto) {
        return new WinnerResponse(winnerDto.winners());
    }
}
