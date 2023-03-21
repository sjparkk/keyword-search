package com.keyword.keywordsearch.service.requester

import com.keyword.keywordsearch.service.dto.ResBlogKeywordsDTO
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
interface BlogSearchRequester {
    fun searchBlogs(keyword: String, pageable: Pageable): ResBlogKeywordsDTO
}