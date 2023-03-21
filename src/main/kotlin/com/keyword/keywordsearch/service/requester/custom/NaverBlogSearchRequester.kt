package com.keyword.keywordsearch.service.requester.custom

import com.keyword.keywordsearch.service.requester.BlogSearchRequester
import com.keyword.keywordsearch.common.client.channel.naver.NaverSearchApiClient
import com.keyword.keywordsearch.common.client.channel.naver.dto.ResNaverBlogCondition
import com.keyword.keywordsearch.common.entity.EntityEnum
import com.keyword.keywordsearch.common.error.CustomBadRequestException
import com.keyword.keywordsearch.common.error.CustomInternalServerException
import com.keyword.keywordsearch.common.error.CustomUnauthorizedException
import com.keyword.keywordsearch.service.dto.BlogKeywordsDTO
import com.keyword.keywordsearch.service.dto.ResBlogKeywordsDTO
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.net.HttpURLConnection
import java.net.URI

@Component
class NaverBlogSearchRequester(
    private val naverSearchApiClient: NaverSearchApiClient,
    private val environment: Environment
) : BlogSearchRequester {

    private val log = LoggerFactory.getLogger(javaClass)

    //naver api key & url
    private val naverClientId = environment.getProperty("feign.target-api.naver-blog-search.client-id")!!
    private val naverClientSecret = environment.getProperty("feign.target-api.naver-blog-search.client-secret")!!
    private val naverBaseUrl = environment.getProperty("feign.target-api.naver-blog-search.base-url")!!

    private val maxRetryCount = 3

    /**
     * 네이버 블로그 검색 API 호출 및 응답값 공통 작업
     */
    override fun searchBlogs(keyword: String, pageable: Pageable): ResBlogKeywordsDTO {

        val naverResponse: ResNaverBlogCondition = naverSearchBlogs(keyword, pageable)
        return ResBlogKeywordsDTO(
            list = naverResponse.items.map {
                BlogKeywordsDTO(
                    title = it.title,
                    contents = it.description,
                    url = it.link,
                    blogname = it.bloggername,
                    datetime = it.postdate
                )
            },
            totalCount = naverResponse.total,
            channelName = EntityEnum.SearchChannelType.NAVER.value
        )
    }

    private fun naverSearchBlogs(
        keyword: String,
        pageable: Pageable
    ): ResNaverBlogCondition {
        lateinit var naverResponse: ResNaverBlogCondition

        try {
            for (i in 0..maxRetryCount) {
                try {
                    naverResponse = naverSearchApiClient.getSearchBlogsByCondition(
                        url = URI(naverBaseUrl),
                        clientId = naverClientId,
                        clientSecret = naverClientSecret,
                        query = keyword,
                        display = pageable.pageSize,
                        start = pageable.pageNumber
                    )
                    log.debug(":: 네이버 블로그 검색 API 정상 응답")
                } catch (e: Exception) {
                    log.debug(":: 네이버 채널 $i 번째 예외 발생")
                    if (i == maxRetryCount) throw e
                }
            }
        } catch (e: Exception) {
            //Exception Handler 를 통한 정의 된 Exception 처리
            when((e as HttpURLConnection).responseCode) {
                HttpStatus.BAD_REQUEST.value() -> throw CustomBadRequestException(e.message)
                HttpStatus.UNAUTHORIZED.value() -> throw CustomUnauthorizedException(e.message)
                HttpStatus.INTERNAL_SERVER_ERROR.value() -> throw CustomInternalServerException(e.message)
            }
        }

        return naverResponse
    }

    override fun getRequesterName(): String {
        return EntityEnum.SearchChannelType.NAVER.value
    }

}