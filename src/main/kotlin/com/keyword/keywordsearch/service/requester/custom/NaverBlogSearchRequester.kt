package com.keyword.keywordsearch.service.requester.custom

import com.keyword.keywordsearch.service.requester.BlogSearchRequester
import com.keyword.keywordsearch.common.client.channel.naver.NaverSearchApiClient
import com.keyword.keywordsearch.common.entity.EntityEnum
import com.keyword.keywordsearch.service.dto.BlogKeywordsDTO
import com.keyword.keywordsearch.service.dto.ResBlogKeywordsDTO
import org.springframework.core.env.Environment
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.net.URI

@Component
class NaverBlogSearchRequester(
    private val naverSearchApiClient: NaverSearchApiClient,
    private val environment: Environment
) : BlogSearchRequester {

    //naver api key & url
    private val naverClientId = environment.getProperty("feign.target-api.naver-blog-search.client-id")!!
    private val naverClientSecret = environment.getProperty("feign.target-api.naver-blog-search.client-secret")!!
    private val naverBaseUrl = environment.getProperty("feign.target-api.naver-blog-search.base-url")!!

    /**
     * 네이버 블로그 검색 API 호출 및 응답값 공통 작업
     */
    override fun searchBlogs(keyword: String, pageable: Pageable): ResBlogKeywordsDTO {

        val naverResponse = naverSearchApiClient.getSearchBlogsByCondition(
            url = URI(naverBaseUrl), clientId = naverClientId, clientSecret = naverClientSecret, query = keyword, display = pageable.pageSize, start = pageable.pageNumber
        )

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

}