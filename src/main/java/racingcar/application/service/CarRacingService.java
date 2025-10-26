package racingcar.application.service;

import racingcar.application.dto.request.StartRaceRequest;
import racingcar.application.dto.response.CarStatusResponse;
import racingcar.application.dto.response.RacingStatusResponse;
import racingcar.application.dto.response.WinnerResponse;
import racingcar.infrastructure.repository.GameRepository;
import racingcar.infrastructure.repository.GameSnap;
import racingcar.domain.car.Car;
import racingcar.domain.car.Cars;
import racingcar.domain.game.GameState;

import java.util.*;

public class CarRacingService{

    private final InputParser inputParser;
    private final RandomGenerator randomGenerator;
    private final GameRepository gameRepository;

    public CarRacingService(InputParser inputParser,
                            RandomGenerator randomGenerator,
                            GameRepository gameRepository) {
        this.inputParser = inputParser;
        this.randomGenerator = randomGenerator;
        this.gameRepository = gameRepository;
    }

    // update 로직
    // 게임 시작 상태를 저장
    public void start(StartRaceRequest startRaceRequest) {
        // 1. carName 추출
        String carNamesInput = startRaceRequest.carNames();
        List<String> carNames = inputParser.parseInput(carNamesInput);

        // 2. carNames List를 LinkedHashMap으로 변경 및 삽입
        Map<String, Car> carMap = new LinkedHashMap<>();
        for (String carName : carNames) {
            Car car = Car.validatedFrom(carName);
            carMap.put(carName, car);
        }
        Cars cars = Cars.validatedOf(carMap);

        // 3. tryCount 추출 및 게임 생성
        Integer tryCount = startRaceRequest.tryCount();
        GameState gameState= GameState.validatedFrom(tryCount);

        // 5. 상태 저장
        gameRepository.save(cars, gameState);
    }

    // read 로직
    // 게임이 진행중인지 조회하여 boolean값 반환
    public boolean hasNextRound() {
        GameSnap gameSnap = gameRepository.getGameSnap();
        return !gameSnap.gameState().isOver();
    }

    // read and write 로직
    // 1개의 라운드를 진행하고 그 결과를 반환
    public RacingStatusResponse getRoundResult() {
        //레파지토리로부터 자동차와 상태 로드
        GameSnap gameSnapResponse = gameRepository.getGameSnap();

        Cars cars = gameSnapResponse.cars();
        GameState gameState = gameSnapResponse.gameState();

        // 각 자동차별로 게임을 진행한다.
        cars.getValues().keySet().forEach(name -> { // 이름을 찾고
            if (randomGenerator.isSuccess()) { // 랜덤 주사위 결과에 성공하면
                cars.moveCar(name); // 해당 이름의 자동차 이동
            }
        });
        // 게임 진행도를 올린다.
        gameState.advance();

        // 다시 상태 저장
        gameRepository.save(cars, gameState);

        // dto 삽입을 위한 cars -> CarStatusResponse 변환
        List<CarStatusResponse> carStatuses = cars.getValues().values()
                .stream()
                .map(car -> {
                    CarStatusResponse carStatus = new CarStatusResponse(car.getName(), car.getPosition());
                    return carStatus;
                }).toList();

        // 레이싱 상태 반환
        return new RacingStatusResponse(carStatuses);
    }

    // read 로직
    // 최종 우승자 반환
    public WinnerResponse getFinalWinners() {
        // 레파지토리로부터 자동차 로드
        GameSnap gameSnap = gameRepository.getGameSnap();

        // 1. Car 리스트로 추출
        Collection<Car> cars = gameSnap.cars().getValues().values();

        // 2. 최고 이동 거리 찾기
        int maxPosition = cars.stream()
                .mapToInt(Car::getPosition)
                .max()
                .orElseThrow(() -> new IllegalStateException("자동차가 존재하지 않습니다."));

        // 3. 공동 우승자 모두 추출
        List<String> winners = cars.stream()
                .filter(car -> car.getPosition() == maxPosition)
                .map(Car::getName)
                .toList();

        // 4. 우승자 반환
        return new WinnerResponse(winners);
    }
}
