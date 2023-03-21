package com.keyword.keywordsearch.common.dto.page

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "페이지 관련 정보", hidden = true)
data class PageInfoDTO(

    @field:Schema(title = "페이지 정보")
    val page: Int? = 1,

    @field:Schema(title = "사이즈")
    val size: Int? = 0,

    @field:Schema(title = "정렬 정보")
    val sort: String? = null,

) {
}

