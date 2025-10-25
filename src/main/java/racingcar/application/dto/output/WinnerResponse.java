package racingcar.application.dto.output;

import java.util.List;

public record WinnerResponse(
        List<String> winners
){
}