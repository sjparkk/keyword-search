package com.keyword.keywordsearch.service

import com.keyword.keywordsearch.common.error.CustomBlogSearchApisServerException
import com.keyword.keywordsearch.domain.BlogKeyword
import com.keyword.keywordsearch.domain.event.SaveBlogSearchKeywordEvent
import com.keyword.keywordsearch.repository.BlogKeywordRepository
import com.keyword.keywordsearch.service.dto.ResBlogKeywordsDTO
import com.keyword.keywordsearch.service.requester.BlogSearchRequester
import com.keyword.keywordsearch.service.requester.custom.KakaoBlogSearchRequester
import com.keyword.keywordsearch.service.requester.custom.NaverBlogSearchRequester
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BlogKeywordService(
    private val blogKeywordRepository: BlogKeywordRepository,
    private val kakaoBlogSearchRequester: KakaoBlogSearchRequester,
    private val naverBlogSearchRequester: NaverBlogSearchRequester,
    private val eventPublisher: ApplicationEventPublisher,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    //블로그 검색 채널 목록
    private val searchRequester : List<BlogSearchRequester> = listOf(kakaoBlogSearchRequester, naverBlogSearchRequester)

    @Transactional(readOnly = true)
    fun getBlogConditionList(keyword: String, pageable: Pageable): ResBlogKeywordsDTO {

        for (blogSearchRequester in searchRequester) {
            try {
                return blogSearchRequester.searchBlogs(keyword, pageable)
            } catch (e: Exception) {
                log.error(":: 블로그 검색 서버 장애 발생")
            } finally {
                //블로그 검색 조회 결과와 상관 없이 사용자 검색 요청 키워드 저장
                eventPublisher.publishEvent(SaveBlogSearchKeywordEvent(keyword))
            }
        }

        throw CustomBlogSearchApisServerException()
    }

    /**
     * 블로그 검색 키워드 저장 이벤트
     */
    @EventListener
    @Transactional
    fun saveKeywordHistoryEvent(event: SaveBlogSearchKeywordEvent) {

        log.debug(":: 검색어 저장 이벤트 발생 - 검색어 = '${event.keyword}'")

        //블로그 검색 키워드와 저장일시 만 저장
        blogKeywordRepository.save(BlogKeyword(keyword = event.keyword))
    }

}