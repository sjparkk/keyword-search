package com.keyword.keywordsearch.service.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.keyword.keywordsearch.repository.dto.PopularKeywordDTO
import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "인기 검색어 목록 정보", hidden = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ResPopularKeywordsDTO(

    @field:Schema(title = "인기 검색어 목록 결과", description = """
        - 최대 10개의 검색 키워드를 제공
        - 10개 미만 시 조회 된 전체 데이터 제공
    """)
    val data: MutableList<PopularKeywordDTO>? = mutableListOf()

) {

}