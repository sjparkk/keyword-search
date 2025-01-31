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


    @PopularKeywordApiResponse
    @GetMapping("/popular/keyword")
    fun getPopularSearchKeywordList(): CommonResponse<ResPopularKeywordsDTO> {
        return CommonResponse(blogKeywordService.getPopularSearchKeywordList())
    }
}