package com.keyword.keywordsearch.service.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "블로그 검색 조회 목록 정보", hidden = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ResBlogKeywordsDTO(

    @field:Schema(title = "블로그 검색 목록 결과", description = """
        - 블로그 검색 조회 API를 시도한 채널의 응답 결과 값입니다
    """)
    val list: List<BlogKeywordsDTO>? = listOf(),

    @field:Schema(title = "검색된 결과 개수")
    val totalCount: Int,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:Schema(title = "totalCount 중에 노출 가능한 수", description = """
        - 카카오 블로그 검색 시 해당 정보 존재
        - 네이버 블로그 검색 시 해당 정보 존재하지 않음
    """)
    val pageableCount: Int? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:Schema(title = "현재 페이지가 마지막 페이지인지 여부", description = """
        - 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
        - 카카오 블로그 검색 시 해당 정보 존재
        - 네이버 블로그 검색 시 해당 정보 존재하지 않음
    """)
    val isEnd: Boolean? = null,

    @field:Schema(title = "검색 결과를 제공한 채널 정보", description = """
        - 카카오 블로그 검색 API 시 'KAKAO'
        - 네이버 블로그 검색 API 시 'NAVER'
    """)
    val channelName: String,

    ) {

}