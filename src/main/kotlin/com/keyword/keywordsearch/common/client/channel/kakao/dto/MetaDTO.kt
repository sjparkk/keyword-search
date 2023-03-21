package com.keyword.keywordsearch.common.client.channel.kakao.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class MetaDTO(

    @field:Schema(title = "검색된 문서 수")
    val totalCount: Int,

    @field:Schema(title = "totalCount 중에 노출 가능한 수")
    val pageableCount: Int,

    @field:Schema(title = "현재 페이지가 마지막 페이지인지 여부", description = """
        - 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
    """)
    val isEnd: Boolean,

) {
}