package com.keyword.keywordsearch.repository.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "인기 검색어 조회 응답")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class PopularKeywordDTO(
    @field:Schema(title = "블로그 검색 키워드")
    var keyword: String? = null,

    @field:Schema(title = "검색 횟수")
    var total: Long? = 0,

) {

}