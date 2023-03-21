package com.keyword.keywordsearch.common.client.channel.kakao

import com.keyword.keywordsearch.common.config.feign.FeignLoggingConfig
import com.keyword.keywordsearch.common.client.channel.kakao.dto.ResKakaoBlogCondition
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import java.net.URI
import javax.validation.constraints.NotBlank

@FeignClient(name = "kakao-api", url = "no-url", configuration = [FeignLoggingConfig::class])
interface KakaoSearchApiClient {

    @GetMapping(consumes = ["application/json"], produces = ["application/json"])
    fun getSearchBlogsByCondition(
        url: URI,
        @RequestHeader(value = "Authorization") auth: String,
        @NotBlank @RequestParam(value = "query", required = true) query: String,
        @RequestParam(value = "sort", required = false) sort: String?,
        @RequestParam(value = "page", required = false) page: Int?,
        @RequestParam(value = "size", required = false) size: Int?
    ): ResKakaoBlogCondition

}