# Blog Keyword Service

## Description
***
- 본 프로젝트는 카카오 블로그 검색 API와 네이버 블로그 검색 API를 이용한 블로그 검색 기능을 제공합니다.
- 추가적으로 사용자들의 검색 키워드를 수집하여 검색 횟수가 가장 많은 인기 검색어 TOP10 키워드에 대한 정보도 제공합니다. 

## Spec
***
* SpringBoot 2.7.9
* Java 11, Kotlin 1.6.21
* Gradle 7.6
* jUnit5
* H2 Database
* QueryDsl 5.0

## API 명세 문서
***
* [Swagger](http://localhost:8081/swagger-ui/index.html)

## 설계 내용

### 실행 방법
* [Jar 경로](https://github.com/sjparkk/keyword-search/tree/main/output)
* java -jar keyword-search.jar

### 주의 사항
* h2 디비 실행 중 content.mv.db가 이미 존재한다는 오류가 나면 app이 여러개 떠있을 수 있으니 확인 부탁드립니다.

### 패키지 구성
1. com.keyword.keywordsearch
    1. controller : 컨트롤러 클래스가 있습니다. 
        1. swagger : 컨트롤러 클래스에서 사용되는 swagger 정의 어노테이션이 있습니다.
    2. service : 서비스 클래스가 있습니다.
        1. dto : 서비스 관련 DTO 클래스가 있습니다.
        2. requester : 서비스 레이어와 외부 API 요청 사이에서 중간자 역할을 합니다. (추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려)
    3. repository : 리파지토리 인터페이스가 있습니다.
        1. custom : 커스텀 리파지토리 인터페이스 및 클래스가 있습니다.
        2. dto : 리포지토리 관련 DTO 클래스가 있습니다.
    4. domain : 도메인 엔티티 클래스가 있습니다.
        1. event : 이벤트 클래스가 있습니다.
    5. common : 공통으로 사용되는 클래스들이 존재합니다.
        1. client : 외부 API 요청을 위한 페인클라이언트를 이용한 인터페이스 및 구현 클래스들로 구성되어 있습니다 (현재 카카오, 네이버 존재)
        2. config : 캐시 / 페인클라이언트 로깅 / QueryDsl / Swagger 컨피그 클래스들로 구성되어 있습니다
        3. entity : 공통 엔티티 관련 클래스들로 구성되어 있습니다.
        4. dto : 공통 응답 클래스 및 페이지 관련 DTO 클래스들로 구성되어 있습니다
        5. error : 공통으로 에러 처리하기 위한 Exception Handler 클래스와 CustomException 클래스들로 구성되어 있습니다

### QueryDsl QClass 생성
1. git update를 받은 후에 querydsl QClass 오류 발생시 gradle clean 후 다시 컴파일합니다.

### 기능 설계
1. 키워드를 통한 블로그 정보 검색 API
    1. 키워드를 통해 블로그를 검색 기능을 제공합니다.
        1. 최초 검색 시 Kakao 블로그 검색 API를 통하여 정보가 전달됩니다.
        2. 예외 발생 시 3번의 retry 후 Naver 블로그 검색 API를 통하여 정보를 전달합니다.
        3. Naver 블로그 검색 API도 마찬가지로 3번의 retry 를 시도합니다.
        4. 예외 발생 시 Exception Handler를 통해 정의 된 Exception 발생 시 CustomException을 통해 예외 정보가 전달됩니다.
    2. 키워드를 통한 블로그 검색 결과가 나올 시 인기 검색어 목록 제공을 위한 검색어 내역 저장 이벤트가 발생됩니다. (인기 검색어 목록 조회 API에 제공 될 데이터)
        1. 이벤트리스너를 통해 동작 된 함수를 통해 검색 키워드가 저장됩니다.
        2. 검색 횟수는 따로 저장하지 않습니다. (동시성 이슈로 인한 데이터 불일치 고려)
    3. page 관련 설정을 제공합니다.
        1. 요청 정보로는 page, size, sort 기능을 제공하며, sort의 경우 정확도순, 최신순 정렬을 제공합니다. (기본값 - 정확도 순)
        2. 응답 정보로는 totalElements(전체 결과 개수), page, size, sort 정보가 제공됩니다.
    4. API 채널별 Docs URL
        1. [Kakao](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog)
        2. [Naver](https://developers.naver.com/docs/serviceapi/search/blog/blog.md)
2. 인기 검색어 목록 조회 API
    1. 사용자들이 많이 검색한 순서대로, 최대 10개 검색 키워드와 검색어 별로 검색된 횟수도 함께 제공합니다. (검색 키워드 10개 이하 시 검색 된 전체 키워드를 제공합니다. ex: 결과 6개 시 6개 제공)
    2. 키워드를 통한 블로그 정보 검색 시 실시간으로 검색어에 대한 검색 횟수를 수정 시 동시성 이슈가 발생할 수 있어 해결 방안으로 스케줄러와 캐시를 사용합니다.
        1. @Synchronized or Redisson을 이용한 분산락을 이용하여 동시성을 보장 받을 수 있지만 '트래픽'이 아주 많은 경우 성능 이슈가 발생할 수 있어 사용 x
        2. 이에 대한 방안으로 스케줄러를 통해 1시간마다 최신 인기 키워드 목록 조회 데이터를 캐시에 저장하여 제공합니다.
 


