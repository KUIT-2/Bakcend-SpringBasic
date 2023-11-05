# spring-basic
KUIT 서버 파트 - 6주차 강의 미션 수행을 위한 레포지토리입니다.


## Branch 설명

---

### main

> 미션 수행 장소

### 6week/servlet-mvc

> 6주차 미션 + HandlerAdapter 구현 코드

#### v1

- ControllerV1 : ModelAndView 객체를 반환하는 Controller
- 실행 : `localhost:8080/v1/…`

#### v2

- ControllerV2 : 뷰 이름(String viewName)을 반환하는 Controller
- 실행 : `localhost:8080/v2/…`

#### v3

- 두 종류의 Controller를 전부 사용하기 위해 HandlerAdapter를 도입
- 실행
    - ControllerV1 : `localhost:8080/v3/v1/…`
    - ControllerV2 : `localhost:8080/v3/v2/…`

### 6week/spring-mvc

> 미션 구현 코드
