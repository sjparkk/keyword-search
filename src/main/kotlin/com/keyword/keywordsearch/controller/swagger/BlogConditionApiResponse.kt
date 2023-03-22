package com.keyword.keywordsearch.controller.swagger

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses


@Operation(summary = "블로그 검색 조회", description =
    """
        - 검색 결과는 정확도순 또는 최신순으로 제공합니다.
        - 카카오 or 네이버 API의 키워드로 블로그 검색 결과를 제공합니다.
        - 인기 검색어 목록 제공을 위해 검색어 내역 저장 이벤트가 발생합니다.
    """)
@ApiResponses(
    ApiResponse(
        responseCode = "400",
        description = """
               1. Incorrect query request - 잘못된 쿼리요청입니다
               2. Invalid display value - 부적절한 display 값입니다
               3. Invalid start value - 부적절한 start 값입니다
               4. Invalid sort value - 부적절한 sort 값입니다
               5. Malformed encoding - 잘못된 형식의 인코딩입니다  
            """
    ),
    ApiResponse(
        responseCode = "403",
        description = """
               1. API 권한 없음
            """
    ),
    ApiResponse(
        responseCode = "404",
        description = """
               1. Invalid search api - 존재하지 않는 검색 api 입니다
            """
    ),
    ApiResponse(
        responseCode = "500",
        description = """
               1. System Error - 시스템 에러입니다.
            """
    ),
)
annotation class BlogConditionApiResponse()
