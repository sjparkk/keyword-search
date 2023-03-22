package com.keyword.keywordsearch.service

import com.keyword.keywordsearch.repository.BlogKeywordRepository
import com.keyword.keywordsearch.repository.dto.PopularKeywordDTO
import com.keyword.keywordsearch.service.requester.custom.KakaoBlogSearchRequester
import com.keyword.keywordsearch.service.requester.custom.NaverBlogSearchRequester
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.cache.CacheManager
import org.springframework.context.ApplicationEventPublisher


@ExtendWith(MockitoExtension::class)
class KeywordServiceTest {

    @InjectMocks
    private lateinit var blogKeywordService: BlogKeywordService

    @Mock
    private lateinit var blogKeywordRepository: BlogKeywordRepository

    @Mock
    private lateinit var kakaoBlogSearchRequester: KakaoBlogSearchRequester

    @Mock
    private lateinit var naverBlogSearchRequester: NaverBlogSearchRequester

    @Mock
    private lateinit var eventPublisher: ApplicationEventPublisher

    @Mock
    private lateinit var cacheManager: CacheManager

    @Test
    @DisplayName("인기 검색어 목록 조회 시 전체 데이터 갯수가 10개 이하일 시 존재하는 전체 데이터를 반환함을 검증한다")
    fun popularKeywordTotalLessThan10Verify() {

        // given
        val blogKeywords = mutableListOf(
            PopularKeywordDTO(keyword = "테스트1", total = 3),
            PopularKeywordDTO(keyword = "테스트2", total = 5)
        )
        given(blogKeywordRepository.findTop10PopularKeywordsOrderByDesc()).willReturn(blogKeywords);

        // when
        val popularKeywordList = blogKeywordService.getPopularSearchKeywordList()

        // then
        Assertions.assertTrue(popularKeywordList.data!!.size < 10)
    }

    @Test
    @DisplayName("인기 검색어 목록 조회 시 최대 10개까지 조회하는지 검증한다")
    fun popularKeywordTotalMaxVerify() {
        // given
        val blogKeywords = mutableListOf(
            PopularKeywordDTO(keyword = "테스트1", total = 3),
            PopularKeywordDTO(keyword = "테스트2", total = 5),
            PopularKeywordDTO(keyword = "테스트3", total = 3),
            PopularKeywordDTO(keyword = "테스트4", total = 5),
            PopularKeywordDTO(keyword = "테스트5", total = 3),
            PopularKeywordDTO(keyword = "테스트6", total = 5),
            PopularKeywordDTO(keyword = "테스트7", total = 3),
            PopularKeywordDTO(keyword = "테스트8", total = 5),
            PopularKeywordDTO(keyword = "테스트9", total = 3),
            PopularKeywordDTO(keyword = "테스트10", total = 5)
        )
        given(blogKeywordRepository.findTop10PopularKeywordsOrderByDesc()).willReturn(blogKeywords);

        // when
        val popularKeywordList = blogKeywordService.getPopularSearchKeywordList()

        // then
        Assertions.assertTrue(popularKeywordList.data!!.size == 10)
    }

    @Test
    @DisplayName("인기 검색어 목록 조회 시 검색 횟수가 높은 순으로 반환되는지 검증한다")
    fun popularKeywordTotalOrderByDescVerify() {
        // given
        val blogKeywords = mutableListOf(
            PopularKeywordDTO(keyword = "테스트1", total = 19),
            PopularKeywordDTO(keyword = "테스트2", total = 12),
            PopularKeywordDTO(keyword = "테스트3", total = 11),
            PopularKeywordDTO(keyword = "테스트4", total = 8),
            PopularKeywordDTO(keyword = "테스트5", total = 7),
            PopularKeywordDTO(keyword = "테스트6", total = 5),
            PopularKeywordDTO(keyword = "테스트7", total = 4),
            PopularKeywordDTO(keyword = "테스트8", total = 3),
            PopularKeywordDTO(keyword = "테스트9", total = 2),
            PopularKeywordDTO(keyword = "테스트10", total = 1)
        )
        given(blogKeywordRepository.findTop10PopularKeywordsOrderByDesc()).willReturn(blogKeywords);

        // when
        val popularKeywordList = blogKeywordService.getPopularSearchKeywordList()
        val largestValue = popularKeywordList.data!!.maxOfOrNull { it.total!! }

        // then
        Assertions.assertEquals(popularKeywordList.data!![0].total, largestValue)
    }

}