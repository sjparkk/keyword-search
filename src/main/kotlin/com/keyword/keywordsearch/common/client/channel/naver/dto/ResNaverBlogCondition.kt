package com.keyword.keywordsearch.common.client.channel.naver.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
class ResNaverBlogCondition(

    @field:Schema(title = "검색 결과를 생성한 시간")
    val lastBuildDate: String,

    @field:Schema(title = "총 검색 결과 개수")
    val total: Int,

    @field:Schema(title = "검색 시작 위치")
    val start: Int,

    @field:Schema(title = "한 번에 표시할 검색 결과 개수")
    val display: Int,

    @field:Schema(title = "검색 결과 리스트")
    val items: List<ItemsDTO>,
) {
}