package com.keyword.keywordsearch.controller

import com.keyword.keywordsearch.common.dto.CommonResponse
import com.keyword.keywordsearch.common.dto.page.PageInfoDTO
import com.keyword.keywordsearch.common.dto.page.ReqPageDTO
import com.keyword.keywordsearch.common.dto.page.ResPageDTO
import com.keyword.keywordsearch.controller.swagger.BlogConditionApiResponse
import com.keyword.keywordsearch.controller.swagger.PopularKeywordApiResponse
import com.keyword.keywordsearch.service.BlogKeywordService
import com.keyword.keywordsearch.service.dto.ResBlogKeywordsDTO
import com.keyword.keywordsearch.service.dto.ResPopularKeywordsDTO
import io.swagger.v3.oas.annotations.Operation
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/api/blog")
class BlogKeywordController(
    val blogKeywordService: BlogKeywordService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Operation(summary = "블로그 검색 조회", description =
    """
        - 검색 결과는 정확도순 또는 최신순으로 제공합니다.
        - 카카오 or 네이버 API의 키워드로 블로그 검색 결과를 제공합니다.
        - 인기 검색어 목록 제공을 위해 검색어 내역 저장 이벤트가 발생합니다.
    """)
    @BlogConditionApiResponse
    @GetMapping("/search/{keyword}")
    fun getBlogConditionList(
        @NotBlank @PathVariable("keyword") keyword: String,
        reqPage: ReqPageDTO
    ): CommonResponse<ResPageDTO<ResBlogKeywordsDTO>> {

        val response = blogKeywordService.getBlogConditionList(keyword, reqPage.getPageable())
        log.debug(":: 블로그 검색 응답 $response")

        return CommonResponse(
            ResPageDTO(
                data = response,
                pageInfo = PageInfoDTO(
                    page = reqPage.page,
                    size = reqPage.size,
                    sort = reqPage.sort
                )
            )
        )
    }

    @Operation(summary = "인기 검색어 목록 조회", description = """
        - 사용자들이 많이 검색한 순서대로 최대 10개의 검색 키워드를 제공합니다.
        - 검색어 별로 검색 된 횟수도 함께 제공합니다.
    """)
    @PopularKeywordApiResponse
    @GetMapping("/popular/keyword")
    fun getPopularSearchKeywordList(): CommonResponse<ResPopularKeywordsDTO> {
        return CommonResponse(blogKeywordService.getPopularSearchKeywordList())
    }
}