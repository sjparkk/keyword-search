package com.keyword.keywordsearch.common.client.channel.naver.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ItemsDTO(

    @field:Schema(title = "블로그 포스트의 제목", description = """
        - 제목에서 검색어와 일치하는 부분은 <b> 태그로 감싸져 있습니다.
    """)
    val title: String,

    @field:Schema(title = "블로그 포스트의 URL")
    val link: String,

    @field:Schema(title = "블로그 포스트의 내용을 요약한 패시지 정보", description = """
        - 패시지 정보에서 검색어와 일치하는 부분은 <b> 태그로 감싸져 있습니다.
    """)
    val description: String,

    @field:Schema(title = "블로그 포스트가 있는 블로그의 이름")
    val bloggername: String,

    @field:Schema(title = "블로그 포스트가 있는 블로그의 주소")
    val bloggerlink: String,

    @field:Schema(title = "블로그 포스트가 작성된 날짜")
    val postdate: String,

    ) {
}