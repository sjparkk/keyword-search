package com.keyword.keywordsearch.service

import com.keyword.keywordsearch.common.error.CustomBlogSearchApisServerException
import com.keyword.keywordsearch.common.error.CustomNotFoundPopularKeyword
import com.keyword.keywordsearch.domain.BlogKeyword
import com.keyword.keywordsearch.domain.event.SaveBlogSearchKeywordEvent
import com.keyword.keywordsearch.repository.BlogKeywordRepository
import com.keyword.keywordsearch.service.dto.ResBlogKeywordsDTO
import com.keyword.keywordsearch.service.dto.ResPopularKeywordsDTO
import com.keyword.keywordsearch.service.requester.BlogSearchRequester
import com.keyword.keywordsearch.service.requester.custom.KakaoBlogSearchRequester
import com.keyword.keywordsearch.service.requester.custom.NaverBlogSearchRequester
import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BlogKeywordService(
    private val blogKeywordRepository: BlogKeywordRepository,
    private val kakaoBlogSearchRequester: KakaoBlogSearchRequester,
    private val naverBlogSearchRequester: NaverBlogSearchRequester,
    private val eventPublisher: ApplicationEventPublisher,
    private val cacheManager: CacheManager
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
                val searchChannelName = blogSearchRequester.getRequesterName()
                log.error(":: $searchChannelName 블로그 검색 서버 장애 발생")
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

    /**
     * 인기 검색어 목록 조회 기능
     */
    @Transactional(readOnly = true)
    @Cacheable(value = ["popularSearchKeyword"])
    fun getPopularSearchKeywordList(): ResPopularKeywordsDTO {
        val popularKeywordResult = blogKeywordRepository.findTop10PopularKeywordsOrderByDesc()

        if(popularKeywordResult.isEmpty()) throw CustomNotFoundPopularKeyword()

        return ResPopularKeywordsDTO(popularKeywordResult)
    }

    /**
     * 인기 검색어 목록 1시간 마다 업데이트
     */
    @CachePut("popularSearchKeyword")
    @Scheduled(cron = "0 0 0/1 * * *") // 1시간
    fun updatePopularSearchKeyword() {
        val newPopularSearchKeywordResult = blogKeywordRepository.findTop10PopularKeywordsOrderByDesc()
        val cache = cacheManager.getCache("popularSearchKeyword")
        cache?.put("popularSearchKeyword", newPopularSearchKeywordResult)
    }

}