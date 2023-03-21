package com.keyword.keywordsearch.repository

import com.keyword.keywordsearch.domain.BlogKeyword
import org.springframework.data.jpa.repository.JpaRepository

interface BlogKeywordRepository: JpaRepository<BlogKeyword, Long> {
}