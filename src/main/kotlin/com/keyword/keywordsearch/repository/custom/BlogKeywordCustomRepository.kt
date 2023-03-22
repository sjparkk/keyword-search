package com.keyword.keywordsearch.repository.custom

import com.keyword.keywordsearch.repository.dto.PopularKeywordDTO

interface BlogKeywordCustomRepository {
    fun findTop10PopularKeywordsOrderByDesc(): MutableList<PopularKeywordDTO>
}