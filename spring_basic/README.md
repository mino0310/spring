# Spring 기본 지식 학습

## 객체 지향 설계와 스프링
- 스프링이란?
- 좋은 객체 지향 프로그램의 정의
- 좋은 객체 지향 설게의 5가지 원칙(SOLID)
- 객체 지향 설게와 스프링

## 스프링 컨테이너와 스프링 빈
- 스프링 컨테이너 생성
- 컨테이너에 등록된 모든 빈 조회
- 스프링 빈 조회
- BeanFactory와 ApplicationContext
- 다양한 빈 생성법 - 자바코드, XML
- 스프링 빈 설정 메타 정보 - BeanDefinition

## 싱글톤 컨테이너
- 웹 어플리케이션과 싱글톤
- 싱글톤 패턴
- 싱글톤 컨테이너
- 싱글톤 방식의 주의점
- @Configuration 과 싱글톤
- @Configuration 과 바이트 코드 조작

## 컴포넌트 스캔
- 컴포넌트 스캔과 의존관계 주입
- 탐색 위치와 기본 스캔 경로
- 필터
- 중복 등록과 충돌

## 의존관계 자동 주입
- 다양한 DI 방법
- 옵션 처리
- @Autowired, @Qualifier, @Primary

## 빈 생명주기 콜백
- Interface InitializingBean, DisposableBean
- 빈 등록 초기화, 소멸 메서드
- @PostConstruct, @PreDestroy

## 빈 스코프
- 프로토 타입 스코프
- 웹 스코프
- 스코프와 Provider
- 스코프와 프록시
