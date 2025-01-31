package com.keyword.keywordsearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableFeignClients
@EnableJpaAuditing
@EnableCaching
@EnableScheduling
@SpringBootApplication
class KeywordSearchApplication

fun main(args: Array<String>) {
    runApplication<KeywordSearchApplication>(*args)
}
