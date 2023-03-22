package com.keyword.keywordsearch.controller.swagger

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@Operation(summary = "인기 검색어 목록 조회", description = """
        - 사용자들이 많이 검색한 순서대로 최대 10개의 검색 키워드를 제공합니다.
        - 검색어 별로 검색 된 횟수도 함께 제공합니다.
    """)
@ApiResponses(
    ApiResponse(
        responseCode = "204",
        description = "인기 키워드 검색 결과가 존재하지 않습니다"
    )
)
annotation class PopularKeywordApiResponse()
