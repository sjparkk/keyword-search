package com.keyword.keywordsearch.service.requester.custom

import com.keyword.keywordsearch.common.client.channel.kakao.KakaoSearchApiClient
import com.keyword.keywordsearch.common.client.channel.kakao.dto.ResKakaoBlogCondition
import com.keyword.keywordsearch.common.entity.EntityEnum
import com.keyword.keywordsearch.service.dto.BlogKeywordsDTO
import com.keyword.keywordsearch.service.dto.ResBlogKeywordsDTO
import com.keyword.keywordsearch.service.requester.BlogSearchRequester
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.net.URI

@Component
class KakaoBlogSearchRequester(
    private val kakaoSearchApiClient: KakaoSearchApiClient,
    private val environment: Environment
) : BlogSearchRequester {

    private val log = LoggerFactory.getLogger(javaClass)

    //kakao api key & url
    private val kakaoRestApiKey = environment.getProperty("feign.target-api.kakao-blog-search.rest-api-key")!!
    private val kakaoBaseUrl = environment.getProperty("feign.target-api.kakao-blog-search.base-url")!!

    private val maxRetryCount = 3

    /**
     * 카카오 블로그 검색 API 호출 및 응답값 공통 작업
     */
    override fun searchBlogs(keyword: String, pageable: Pageable): ResBlogKeywordsDTO {

        val sort = pageable.sort.toString().split(":")[0]

        val kakaoResponse = kakaoSearchBlogs(keyword, sort, pageable)

        return ResBlogKeywordsDTO(
            list = kakaoResponse.documents.map {
                BlogKeywordsDTO(
                    title = it.title,
                    contents = it.contents,
                    url = it.url,
                    blogname = it.blogname,
                    thumbnail = it.thumbnail,
                    datetime = it.datetime
                )
            },
            isEnd = kakaoResponse.meta.isEnd,
            totalCount = kakaoResponse.meta.totalCount,
            pageableCount = kakaoResponse.meta.pageableCount,
            channelName = EntityEnum.SearchChannelType.KAKAO.value
        )
    }

    private fun kakaoSearchBlogs(
        keyword: String,
        sort: String,
        pageable: Pageable
    ): ResKakaoBlogCondition {
        lateinit var kakaoResponse: ResKakaoBlogCondition

        for (i in 0..maxRetryCount) {
            try {
                kakaoResponse = kakaoSearchApiClient.getSearchBlogsByCondition(
                    url = URI(kakaoBaseUrl),
                    auth = kakaoRestApiKey,
                    query = keyword,
                    sort = sort,
                    page = pageable.pageNumber,
                    size = pageable.pageSize
                )
                log.debug(":: 카카오 블로그 검색 API 정상 응답")
            } catch (e: Exception) {
                log.debug(":: 카카오 채널 $i 번째 예외 발생")
                if (i == maxRetryCount) throw e
            }
        }
        return kakaoResponse
    }
}