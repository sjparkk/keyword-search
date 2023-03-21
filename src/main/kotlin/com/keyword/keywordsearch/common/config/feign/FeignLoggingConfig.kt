package com.keyword.keywordsearch.common.config.feign

import feign.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignLoggingConfig {
    @Bean
    fun feignLoggerLevel(): Logger.Level? {
        return Logger.Level.FULL
    }
}