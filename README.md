# 🌼 동시편집 기능을 제공하는 릴리즈 노트 공유 시스템, A-LOG

**개발 기간** 2023.05 ~ 2023.08 <br/>
**사이트 바로가기** https://alog.acceler.kr/ (🔧업데이트 중) <br/>
**Team repo** https://github.com/orgs/KEA-ACCELER/repositories <br/>

# 😺 Stacks

<p>
  <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white">
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">
  <img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white">
  <img src="https://img.shields.io/badge/elastic cloud-005571?style=for-the-badge&logo=Elastic Cloud&logoColor=white">
  <img src="https://img.shields.io/badge/Github Actions-2088FF?style=for-the-badge&logo=Github Actions&logoColor=white">
</p>

# 🐳 Service Architecture

![alog-project](/resource/alog.png)

# 📚 Project Structure

```
src/main/java/kea/alog/project
├── common
│   ├── constant
│   ├── dto
│   ├── entity
│   ├── exception
│   ├── interceptor
│   ├── openFeign
│   └── util
├── config
└── domain
    ├── project
    │   ├── constant
    │   ├── controller
    │   ├── dto
    │   ├── entity
    │   ├── mapper
    │   ├── repository
    │   └── service
    ├── projectMember
    │   ├── controller
    │   ├── dto
    │   ├── entity
    │   ├── repository
    │   └── service
    └── topic
        ├── constant
        ├── controller
        ├── dto
        ├── entity
        ├── mapper
        ├── repository
        └── service

```

## Components

- common
  - 공통적으로 사용될 파일로 구성
  - `constant`, `dto`, `entity`, `exception`, `interceptor`, `openFeign`, `util`로 구성
  - `openFeign`의 경우 알림 서비스와의 통신 쉽게 하기 위해 존재
  - `interceptor`의 경우 인증 서비스에서 JWT 유효성을 판단하고 넘어온 정보를 기준으로 에러 발생 혹은 유저 정보 삽입하는 JwtInterceptor 존재
- config
  - cors 설정 및 swagger 설정 등의 파일로 구성
- domain
  - 도메인 별로 디렉토리 구성
  - 각각의 디렉토리에는 `constant`, `controller`, `dto`, `entity`, `mapper`, `repository`, `service` 존재
  - `project`
    - 프로젝트 관련 도메인
  - `projectMember`
    - 프로젝트 구성원 관련 도메인
  - `topic`
    - 프로젝트 하위 개념인 토픽 관련 도메인

# 🙂 성능테스트 with Ngrinder

A-LOG 프로젝트 서비스의 `GET /api/projects?teamPk=&page=&size=&sortType`으로 팀 별 프로젝트 조회 시 teamPk를 인덱스로 넣었을 때 성능이 얼마나 개선되는지 [Ngrinder](!https://naver.github.io/ngrinder/)를 통해 확인

- 프로시저를 통해 관련 더미 데이터 3만개 삽입
- vUser 3천명

**인덱스 사용하지 않았을 경우**
![ngrinder-without-idx](/resource/ngrinder-without-idx.png)

**적절한 인덱스 사용한 경우**
![ngrinder-with-idx](/resource/ngrinder-with-idx.png)

랜덤한 teamPk로 팀 별 프로젝트 조회 API를 호출했을 때 인덱스 적용 후 **tps 약 50배 증가**

- 단일 WAS에서 인덱스를 적용하지 않았을 경우 tps는 7.4
- 인덱스 적용 후에는 tps가 354.5

# ✨ Installation

## Add docker-compose.yml

```
version: '3'
services:
  web:
    build: ./
    environment:
      - SPRING_DATASOURCE_URL=
      - SPRING_DATASOURCE_USERNAME=
      - SPRING_DATASOURCE_PASSWORD=
      - NOTICE_SERVICE_URL=
    ports:
      - "30000:8083"
    depends_on:
      db:
        condition: service_healthy
  db:
    image: mysql:latest
    environment:
      - MYSQL_DATABASE=
      - MYSQL_ROOT_PASSWORD=
    healthcheck:
      test: [ "CMD", "mysqladmin" ,"ping", "-h", "localhost" ]
      interval: 10s
      timeout: 2s
      retries: 10
```

## Running the app

- 도커 컨테이너 실행

```
docker compose up -d
```

# 🍀 Swagger

```
http://localhost:30000/api/projects
```

![swagger](/resource/swagger.png)
