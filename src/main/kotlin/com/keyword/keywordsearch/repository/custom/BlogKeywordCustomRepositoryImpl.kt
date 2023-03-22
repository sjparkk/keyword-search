package com.keyword.keywordsearch.repository.custom

import com.keyword.keywordsearch.domain.QBlogKeyword.blogKeyword
import com.keyword.keywordsearch.repository.dto.PopularKeywordDTO
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory

class BlogKeywordCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): BlogKeywordCustomRepository {

    override fun findTop10PopularKeywordsOrderByDesc(): MutableList<PopularKeywordDTO> {

        val aliasCount = Expressions.stringPath("total")

        return  queryFactory
            .select(
                Projections.fields(
                    PopularKeywordDTO::class.java,
                    blogKeyword.keyword.`as`("keyword"),
                    blogKeyword.keyword.count().`as`("total")
                )
            )
            .from(blogKeyword)
            .groupBy(blogKeyword.keyword)
            .orderBy(aliasCount.desc())
            .limit(10)
            .fetch()



//        val today = LocalDate.now()
//        val createdAt = LocalDateTime.of(today, LocalTime.MIN)
//        val endedAt = LocalDateTime.of(today, LocalTime.MAX)
//
//        val aliasCount = Expressions.stringPath("search_count")
//
//        return  queryFactory
//            .select(
//                Projections.fields(
//                    PopularKeywordDTO::class.java,
//                    blogKeyword.keyword.`as`("keyword"),
//                    blogKeyword.keyword.count().`as`("count"),
//                    ExpressionUtils.`as`(
//                        JPAExpressions.select(
//                            statisticsBlogKeywordHistory.count.sum()
//                        ).from(statisticsBlogKeywordHistory)
//                            .where(blogKeyword.keyword.eq(statisticsBlogKeywordHistory.keyword))
//                            .groupBy(statisticsBlogKeywordHistory.keyword)
//                            , "statisticsCount"
//                    )
//                )
//            )
//            .from(blogKeyword)
//            .where(
//                blogKeyword.createdAt.between(createdAt, endedAt)
//            )
//            .orderBy(aliasCount.desc())
//            .limit(10)
//            .fetch()

    }
}