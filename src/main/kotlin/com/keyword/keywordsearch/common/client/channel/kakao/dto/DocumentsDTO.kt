package com.keyword.keywordsearch.common.client.channel.kakao.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.swagger.v3.oas.annotations.media.Schema

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DocumentsDTO(

    @field:Schema(title = "블로그 글 제목")
    val title: String,

    @field:Schema(title = "블로그 글 요약")
    val contents: String,

    @field:Schema(title = "블로그 글 URL")
    val url: String,

    @field:Schema(title = "블로그의 이름")
    val blogname: String,

    @field:Schema(title = "미리보기 이미지 URL", description = """
        - 검색 시스템에서 추출한 대표 미리보기 이미지 URL 
        - 미리보기 크기 및 화질은 변경될 수 있음
    """)
    val thumbnail: String,

    @field:Schema(title = "블로그 글 작성시간", description = """
        - ISO 8601
        - [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    """)
    val datetime: String,
) {

}