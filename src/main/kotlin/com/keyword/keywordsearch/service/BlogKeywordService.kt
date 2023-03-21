package com.keyword.keywordsearch.service

import com.keyword.keywordsearch.repository.BlogKeywordRepository
import org.springframework.stereotype.Service

@Service
class BlogKeywordService(
    val blogKeywordRepository: BlogKeywordRepository
) {
}