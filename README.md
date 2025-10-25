# java-racingcar-precourse

## 서비스 흐름
1. 메인 서비스가 인풋 뷰 실행
2. 인풋 뷰가 전달한 데이터를 메인 서비스가 파서에 전달 
3. 파서가 각 데이터파싱 및 파싱한 항목에 대해 car 엔티티를 생성하여 리스트로 만들고 횟수도 포함하여 dto 생성 후 메인 서비스에 반환
4. 메인 서비스가 다시 racingService를 호출
5. racingService가 dto를 받아 레이싱 로직 수행 후 raceStateResponse 리스트를 메인 서비스에 반환
6. 파서가 리스폰스를 해체하여 리스트로 outputView에 전달
7. outputView가 리스폰스를 출력

## 기능 정의
### inputView:  
문자열을 입력받는데, 각 문자열은 쉼표(,)를 기준으로 구분되고, 구분되는 항목은 각각은 자동차의 이름을 의미한다.
템플릿 문자열을 출력 후 입력값을 입력받아 포장하여 반환한다.  
```bash
경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분) # input_template
> pobi,woni,jun # input1
시도할 횟수는 몇 회인가요? # input_template
> 5 # input2
```
  
### outputView:  
리스트를 입력받는데, 각 리스트는 자동차의 이름과 해당 자동차가 이동한 거리를 포함한 raceStateResponse 리스트를 입력받는다.
해당 뷰는 리스폰스를 해체하여 각 자동차의 이름과 이동거리를 출력한다.

해체 포맷은 다음과 같이 구성된다.
``` bash
실행 결과 # output_template1
pobi : -
woni : 
jun : -
# output[1][1-end]
pobi : --
woni : -
jun : --


# output[2][1-end]
pobi : ---
woni : --
jun : ---
# output[3][1-end]
pobi : ----
woni : ---
jun : ----
# output[4][1-end]
pobi : -----
woni : ----
jun : -----
# output[5][1-end]

최종 우승자 : pobi, jun
```
### InputView:
사용자로부터 입력을 받는 기능을 담당한다.
- 템플릿을 출력하는 기능
- 자동차 이름 입력을 위한 템플릿 출력 및 입력값 받기
- 시도 횟수 입력을 위한 템플릿 출력 및 입력값 받기
- 입력값을 포장하여 반환하는 기능

### OutputView:
레이싱 게임의 결과를 출력하는 기능을 담당한다.
- 템플릿을 출력하는 기능
- 레이싱 게임의 각 라운드 결과를 출력하는 기능
- 최종 우승자를 출력하는 기능

### RacingCarController:
레이싱 게임의 전체 흐름을 제어하는 기능을 담당한다.
- InputView로부터 입력값을 받아 assembler를 호출하여 포장하여 CarRacingUseCase에 전달
- CarRacingUseCase로부터 받은 남은 횟수 상태 여부만큼 반복하여 CarRacingUseCase를 호출
- CarRacingUseCase로부터 받은 raceStateResponse를 받아 다시 OutputView에 전달하여 출력
- 최종 우승자를 CarRacingUseCase로부터 받은 결과로 Assembler를 통해 리패킹하고 OutputView에 전달하여 출력

### Assembler:
입력값과 출력값을 포장하고 해체하는 기능을 담당한다. presentation layer와 application layer 간의 서비스 분리를 의식한, 계층간의 Dto 변환을 위해 존재한다.
- StartRaceAssembler: 도착한 Request를 DTO로 포장하는 기능 Application Layer에 종속
- RacingResponseAssembler: 도착한 DTO를 Response로 포장하는 기능 Presentation Layer에 종속

### CarRacingUseCase:
레이싱 게임의 유스케이스를 담당하는 객체로서 다음과 같은 기능을 담당한다.
- 도착한 dto를 기반으로 레이싱 게임을 수행할 준비를 진행할 수 있게 도메인 객체들을 생성 및 초기화
- 레이싱 게임의 각 라운드가 종료될 때마다 각 자동차의 상태를 조작하고 그 결과를 반환
- 최종 우승자를 조회하여 반환
- 레이싱 게임의 완료 여부를 판단할 수 있는 상태 반환 메서드 기능 포함

### CarRacingService:
CarRacingUseCase의 구현체로서 실제 레이싱 게임의 비즈니스 로직을 담당한다.

### randomGenerator:
랜덤 숫자를 생성하는 기능을 담당한다.
0 이상 9 이하의 숫자를 랜덤으로 생성하여 반환하는 기능을 담당한다.

### car:
자동차 엔티티로서 다음과 같은 속성과 기능을 가진다.
- 속성: 이름, 현재 위치
- 기능: 이동 기능 (랜덤 숫자가 4 이상일 경우 위치 1 증가)
- 기능: 현재 위치 조회

### cars:
자동차 엔티티들을 관리하는 컬렉션 객체로서 다음과 같은 기능을 담당한다.
- 속성: 자동차 리스트
- 기능: 자동차 추가
- 기능: 모든 자동차의 현재 위치 조회
- 기능: 모든 자동차 이동 (각 자동차의 이동 기능 호출)
- 기능: 우승자 조회 (가장 멀리 이동한 자동차 이름 조회)

### GameRound:
레이싱 게임의 라운드를 나타내는 객체로서 다음과 같은 속성과 기능을 가진다.
- 속성: 현재 라운드 번호
- 기능: 라운드 번호 증가
- 기능: 현재 라운드 번호 조회
- 기능: 남은 라운드 여부 조회