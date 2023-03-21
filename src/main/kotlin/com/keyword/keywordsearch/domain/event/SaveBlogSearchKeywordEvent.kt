package com.keyword.keywordsearch.domain.event

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "블로그 검색 대상 키워드 저장 이벤트 객체", hidden = true)
data class SaveBlogSearchKeywordEvent(

    @field:Schema(title = "검색 키워드")
    val keyword: String

) {
}