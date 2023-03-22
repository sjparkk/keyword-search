package com.keyword.keywordsearch.common.config.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class QueryDslConfig (@PersistenceContext val entityManager: EntityManager) {
   @Bean
   fun jpqQueryFactory() = JPAQueryFactory(entityManager)
}