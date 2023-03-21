package com.keyword.keywordsearch.service.requester.custom

import com.keyword.keywordsearch.common.client.channel.kakao.KakaoSearchApiClient
import com.keyword.keywordsearch.common.entity.EntityEnum
import com.keyword.keywordsearch.service.dto.BlogKeywordsDTO
import com.keyword.keywordsearch.service.dto.ResBlogKeywordsDTO
import com.keyword.keywordsearch.service.requester.BlogSearchRequester
import org.springframework.core.env.Environment
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.net.URI

@Component
class KakaoBlogSearchRequester(
    private val kakaoSearchApiClient: KakaoSearchApiClient,
    private val environment: Environment
) : BlogSearchRequester {

    //kakao api key & url
    private val kakaoRestApiKey = environment.getProperty("feign.target-api.kakao-blog-search.rest-api-key")!!
    private val kakaoBaseUrl = environment.getProperty("feign.target-api.kakao-blog-search.base-url")!!

    /**
     * 카카오 블로그 검색 API 호출 및 응답값 공통 작업
     */
    override fun searchBlogs(keyword: String, pageable: Pageable): ResBlogKeywordsDTO {

        val sort = pageable.sort.toString().split(":")[0]

        val kakaoResponse = kakaoSearchApiClient.getSearchBlogsByCondition(
            url = URI(kakaoBaseUrl), auth = kakaoRestApiKey, query = keyword, sort = sort, page = pageable.pageNumber, size = pageable.pageSize
        )

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
}