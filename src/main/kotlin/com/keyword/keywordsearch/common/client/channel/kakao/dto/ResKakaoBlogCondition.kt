package com.keyword.keywordsearch.common.client.channel.kakao.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ResKakaoBlogCondition(

    @field:Schema(title = "검색 결과 리스트")
    val documents: List<DocumentsDTO>,

    @field:Schema(title = "검색 메타데이터")
    val meta: MetaDTO
) {
}