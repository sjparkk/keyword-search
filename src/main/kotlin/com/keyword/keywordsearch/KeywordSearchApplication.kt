package com.keyword.keywordsearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class KeywordSearchApplication

fun main(args: Array<String>) {
    runApplication<KeywordSearchApplication>(*args)
}
