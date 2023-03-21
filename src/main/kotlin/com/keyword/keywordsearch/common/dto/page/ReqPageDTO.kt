package com.keyword.keywordsearch.common.dto.page

import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

@ParameterObject
class ReqPageDTO(

    @field:Parameter(description = "page 기본값", hidden = true)
    val defaultPage: Int = 1,

    @field:Parameter(description = "size 기본값", hidden = true)
    val defaultSize: Int = 10,

    @field:Parameter(name = "page", description = "## 조회할 페이지 번호\n", example = "1")
    var page: Int? = null,

    @field:Parameter(name = "size", description = "## 한페이지당 데이터 ROW 수\n"+
            "`size` 에 따라서 전체 페이지수가 달라짐\n" +
            "기본값은 `10`, 보내지 않으면 한 페이지 당 `10` 데이터를 응답", example = "10")
    var size: Int? = null,

) {
    private val ORDER_DESC = "DESC"
    private val ORDER_ASC = "ASC"

    @field:Parameter(name = "sort", description = "## 정렬\n" +
            "- 정확도순 오름차순 정렬 - `accuracy,asc`\n" +
            "- 정확도순 내림차순 정렬 - `accuracy,desc`\n" +
            "- 최신순 오름차순 정렬 - `recency,asc`\n" +
            "- 최신순 내림차순 정렬 - `recency,desc`\n"
        , example = "accuracy,desc")
    var sort: String? = null

    /**
     * 요청한 Pageable 변수로 Pageable 객체 생성
     * @return Pageable
     */
    fun getPageable(): Pageable =
        of(this.page ?: defaultPage, this.size ?: defaultSize, this.sort)

    /**
     * Pageable 객체 생성
     * @param page Int
     * @param size Int
     * @param sort String?
     * @return Pageable
     */
    private fun of(page: Int, size: Int, sort: String?): Pageable {
        if(sort.isNullOrEmpty())
            return PageRequest.of(page, size)
        return PageRequest.of(page, size, sortBy(sort))
    }

    /**
     * sort by 생성
     * @param sort String
     * @return Sort
     */
    private fun sortBy(sort: String): Sort {
        val sorts = sort.split(",")
        if (sorts.size < 2)
            return Sort.by(sort).descending()

        val sort = sorts[0]
        return when (sorts[1].uppercase()) {
            ORDER_DESC -> Sort.by(sort).descending()
            ORDER_ASC -> Sort.by(sort).ascending()
            else -> Sort.unsorted()
        }
    }

}