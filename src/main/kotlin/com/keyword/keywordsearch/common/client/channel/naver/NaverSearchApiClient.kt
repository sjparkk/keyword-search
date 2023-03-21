package com.keyword.keywordsearch.common.client.channel.naver

import com.keyword.keywordsearch.common.config.feign.FeignLoggingConfig
import com.keyword.keywordsearch.common.client.channel.naver.dto.ResNaverBlogCondition
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import java.net.URI
import javax.validation.constraints.NotBlank

@FeignClient(name = "naver-api", url = "no-url", configuration = [FeignLoggingConfig::class])
interface NaverSearchApiClient {

    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun getSearchBlogsByCondition(
        url: URI,
        @RequestHeader(value = "X-Naver-Client-Id") clientId: String,
        @RequestHeader(value = "X-Naver-Client-Secret") clientSecret: String,
        @NotBlank @RequestParam(value = "query", required = true) query: String,
        @RequestParam(value = "display", required = false) display: Int? = 10,
        @RequestParam(value = "start", required = false) start: Int? = 1,
        @RequestParam(value = "sort", required = false) sort: String? = "sim"
    ): ResNaverBlogCondition

}