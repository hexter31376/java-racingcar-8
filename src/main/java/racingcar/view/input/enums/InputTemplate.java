package racingcar.view.input.enums;

public enum InputTemplate {
    INPUT_CAR_NAMES_MESSAGE("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)"),
    INPUT_TRY_COUNT_MESSAGE("시도할 회수는 몇 회인가요?");

    private final String message;

    InputTemplate(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}