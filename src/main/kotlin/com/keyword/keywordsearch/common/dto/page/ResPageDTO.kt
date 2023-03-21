package com.keyword.keywordsearch.common.dto.page

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Schema(title = "공통 페이지 응답 정보", hidden = true)
data class ResPageDTO<T> (

    @field:Schema(title = "응답 데이터 정보")
    val data: T? = null,

    @field:Schema(title = "페이지 관련 정보")
    val pageInfo: PageInfoDTO,
)