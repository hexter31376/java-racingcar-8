package racingcar.application.port.in;

import racingcar.application.dto.response.RacingStatusDto;
import racingcar.application.dto.response.WinnerDto;
import racingcar.presentation.dto.request.StartRaceRequest;
import racingcar.presentation.dto.response.RacingStatusResponse;
import racingcar.presentation.dto.response.WinnerResponse;

public interface CarRacingUseCase {
    // 레이싱 스타트 요청 메서드
    void start(StartRaceRequest startRaceRequest);
    // 다음 라운드 존재 여부 확인 메서드
    boolean hasNextRound();
    // 다음 라운드 진행 및 결과 반환 메서드
    RacingStatusDto getRoundResult();
    // 최종 우승자 반환 메서드
    WinnerDto getFinalWinners();
}
