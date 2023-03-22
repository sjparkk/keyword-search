package com.keyword.keywordsearch.controller.swagger

import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

@ApiResponses(
    ApiResponse(
        responseCode = "204",
        description = "인기 키워드 검색 결과가 존재하지 않습니다"
    )
)
annotation class PopularKeywordApiResponse()
