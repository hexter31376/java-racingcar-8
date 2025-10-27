// java
package racingcar.application.service;

import org.junit.jupiter.api.Test;
import racingcar.application.dto.request.StartRaceRequest;
import racingcar.application.dto.response.RacingStatusResponse;
import racingcar.application.dto.response.WinnerResponse;
import racingcar.domain.car.Car;
import racingcar.domain.car.Cars;
import racingcar.domain.game.GameState;
import racingcar.infrastructure.repository.GameRepository;
import racingcar.infrastructure.repository.GameSnap;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class CarRacingServiceTest {

    @Test
    void start_should_save_cars_and_gameState() {
        // arrange: 테스트 더블 준비 (InputParser, RandomGenerator, Repository)
        FakeInputParser inputParser = new FakeInputParser();
        inputParser.setParsed(List.of("a", "b"));
        StubRandomGenerator randomGenerator = new StubRandomGenerator();
        FakeGameRepository repo = new FakeGameRepository();

        // SUT 생성
        CarRacingService service = new CarRacingService(inputParser, randomGenerator, repo);

        // 요청 생성 (원본 문자열과 시도 횟수)
        StartRaceRequest req = new StartRaceRequest("a,b", 5);

        // act: 서비스 시작 호출
        service.start(req);

        // assert: 저장된 Cars와 GameState가 존재하는지 및 초기 상태 검증
        Cars savedCars = repo.getLastSavedCars();
        GameState savedState = repo.getLastSavedGameState();

        assertThat(savedCars).isNotNull();
        assertThat(savedState).isNotNull();

        // 입력 순서가 보존되어야 함 (LinkedHashMap 기반)
        assertThat(savedCars.getValues().keySet()).containsExactly("a", "b");

        // 모든 자동차의 초기 위치는 0이어야 함
        assertThat(savedCars.getValues().values())
                .allMatch(car -> car.getPosition() == 0);
    }

    @Test
    void getRoundResult_should_move_only_when_randomGenerator_succeeds() {
        // arrange: 테스트 더블 준비
        FakeInputParser inputParser = new FakeInputParser();
        StubRandomGenerator randomGenerator = new StubRandomGenerator();
        // 첫 자동차만 이동하도록 시퀀스 설정 (true, false)
        randomGenerator.setSequence(true, false);

        FakeGameRepository repo = new FakeGameRepository();

        // 초기 Cars/State 구성 (c1, c2)
        Map<String, Car> map = new LinkedHashMap<>();
        map.put("c1", Car.from("c1"));
        map.put("c2", Car.from("c2"));
        Cars cars = Cars.of(map);
        GameState state = GameState.from(1);
        repo.save(cars, state);

        CarRacingService service = new CarRacingService(inputParser, randomGenerator, repo);

        // act: 한 라운드 실행
        RacingStatusResponse response = service.getRoundResult();

        // assert: 반환된 상태의 리스트 크기는 자동차 수와 같아야 함
        assertThat(response.carStatuses()).hasSize(2);

        // 레파지토리에 저장된 상태도 이동 반영되어야 함 (도메인 객체 검사)
        Cars saved = repo.getLastSavedCars();
        assertThat(saved.getValues().get("c1").getPosition()).isEqualTo(1);
        assertThat(saved.getValues().get("c2").getPosition()).isEqualTo(0);
    }

    @Test
    void getFinalWinners_should_return_all_with_max_position() {
        // arrange: 테스트 더블 준비
        FakeInputParser inputParser = new FakeInputParser();
        StubRandomGenerator randomGenerator = new StubRandomGenerator();
        FakeGameRepository repo = new FakeGameRepository();

        // 자동차 3대 준비 (a, b, c)
        Map<String, Car> map = new LinkedHashMap<>();
        map.put("a", Car.from("a"));
        map.put("b", Car.from("b"));
        map.put("c", Car.from("c"));
        Cars cars = Cars.of(map);
        GameState state = GameState.from(1);

        // b와 c를 각각 두 번 이동시켜 동점 상태를 만듦
        cars.moveCar("b");
        cars.moveCar("b");
        cars.moveCar("c");
        cars.moveCar("c");

        repo.save(cars, state);

        CarRacingService service = new CarRacingService(inputParser, randomGenerator, repo);

        // act: 최종 우승자 조회
        WinnerResponse winners = service.getFinalWinners();

        // assert: b와 c만 우승자로 반환 (순서 무관)
        assertThat(winners.winners()).containsExactlyInAnyOrder("b", "c");
    }

    // --- helpers / 테스트 더블들 ---

    // 간단한 InputParser 스텁: 서비스.start에서 사용되며 미리 설정한 리스트를 반환
    static class FakeInputParser extends InputParser {
        private List<String> parsed = List.of();

        void setParsed(List<String> parsed) {
            this.parsed = List.copyOf(parsed);
        }

        @Override
        public List<String> parse(String input) {
            // 입력 문자열은 무시하고 미리 설정한 값을 반환
            return parsed;
        }
    }

    // 결정론적 RandomGenerator 스텁: 미리 설정한 boolean 시퀀스를 반환
    static class StubRandomGenerator extends RandomGenerator {
        private final Queue<Boolean> seq = new ArrayDeque<>();

        void setSequence(Boolean... values) {
            seq.clear();
            for (Boolean v : values) seq.add(v);
        }

        @Override
        public boolean isSuccess() {
            Boolean next = seq.poll();
            // 시퀀스 소진 시 기본 false 반환
            return next != null ? next : false;
        }
    }

    // Fake repository: 마지막으로 save된 Cars와 GameState를 보관하고 GameSnap을 반환
    static class FakeGameRepository extends GameRepository {
        private Cars lastSavedCars;
        private GameState lastSavedGameState;

        @Override
        public void save(Cars cars, GameState gameState) {
            // 참조를 저장 (서비스가 이후에 객체를 변경할 수 있음)
            this.lastSavedCars = cars;
            this.lastSavedGameState = gameState;
        }

        @Override
        public GameSnap getGameSnap() {
            // 저장된 값을 기반으로 GameSnap 생성 (GameSnap의 생성자/레코드 가정)
            return new GameSnap(lastSavedCars, lastSavedGameState);
        }

        Cars getLastSavedCars() {
            return lastSavedCars;
        }

        GameState getLastSavedGameState() {
            return lastSavedGameState;
        }
    }

    // AssertJ의 Tuple 생성을 돕는 간단 헬퍼
    private static org.assertj.core.groups.Tuple tuple(Object... values) {
        return org.assertj.core.groups.Tuple.tuple(values);
    }
}