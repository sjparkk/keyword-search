package com.keyword.keywordsearch.service.dto

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 블로그 검색 정보 공통 DTO 객체
 */
@Schema(title = "블로그 검색 정보", hidden = true)
data class BlogKeywordsDTO(

    @field:Schema(title = "블로그 글 제목")
    val title: String,

    @field:Schema(title = "블로그 글 요약")
    val contents: String,

    //@JsonInclude(JsonInclude.Include.NON_NULL)를 통해 네이버 블로그 검색 조회 응답 시 전달 x
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:Schema(title = "블로그 글 URL", description = """
        - 카카오 블로그 검색 시 해당 정보 존재
        - 네이버 블로그 검색 시 해당 정보 존재하지 않음
    """)
    val url: String? = null,

    @field:Schema(title = "블로그의 이름")
    val blogname: String,

    //@JsonInclude(JsonInclude.Include.NON_NULL)를 통해 네이버 블로그 검색 조회 응답 시 전달 x
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field:Schema(title = "미리보기 이미지 URL", description = """
        - 검색 시스템에서 추출한 대표 미리보기 이미지 URL 
        - 미리보기 크기 및 화질은 변경될 수 있음
        
        - 카카오 블로그 검색 시 해당 정보 존재
        - 네이버 블로그 검색 시 해당 정보 존재하지 않음
    """)
    val thumbnail: String? = null,

    @field:Schema(title = "블로그 글 작성시간", description = """
        - ISO 8601
        - [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    """)
    val datetime: String,

    ) {
}