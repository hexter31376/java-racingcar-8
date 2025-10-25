package racingcar.view.output.enums;

public enum OutputTemplate {
    OUTPUT_TITLE_MESSAGE("실행 결과"),
    OUTPUT_WINNER_MESSAGE("최종 우승자 : ");

    private final String message;

    OutputTemplate(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
