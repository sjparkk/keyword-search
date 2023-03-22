package com.keyword.keywordsearch.controller

import com.keyword.keywordsearch.controller.setup.BlogKeywordSetup
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.transaction.annotation.Transactional
import kotlin.random.Random

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8081)
@Transactional
@Rollback
class BlogKeywordControllerTest {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var blogKeywordSetup: BlogKeywordSetup

    //테스트에 사용 될 검색 키워드 목록
    val keywords = listOf("건강", "웰니스", "여행 목적지", "가정 장식", "육아 조언", "피트니스", "개인 금융",
        "은퇴 계획", "데이트", "레시피", "투자 전략", "패션 트렌드", "미용 제품", "결혼 계획", "보육 서비스", "온라인 과정",
        "시간 관리", "재택근무", "비즈니스", "애완 동물", "자기 개선", "기술 뉴스", "게임 팁", "주택 개조", "스포츠 뉴스",
        "자동차 리뷰", "음악", "영화", "레스토랑 리뷰", "서평", "가전 리뷰", "홈 보안", "관심사", "정신 건강", "자원봉사",
        "소셜 미디어", "온라인 포럼", "연예인", "뉴스 분석", "문화 행사", "정치 뉴스", "웹사이트", "세금", "온라인 뱅킹",
        "금융 서비스", "온라인 법률", "온라인 쇼핑", "집 청소", "아파트", "빌라")

    @Nested
    inner class ApiGetBlogConditionList {

        @BeforeEach
        fun setup() {
            blogKeywordSetup.save()
            log.debug(":: BlogKeyword Data Setup Complete")
        }

        @Test
        @DisplayName("블로그 검색 조회 테스트")
        fun getBlogConditionList() {

            // when
            val resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/blog/search/{keyword}", keywords[Random.nextInt(keywords.size+1)])
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .param("sort", "accuracy,desc")
                    .param("page", "1")
                    .param("size", "10")
            )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "blog-condition-list-get", Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.pathParameters(
                            RequestDocumentation.parameterWithName("keyword").description("검색 키워드")
                        ),
                        RequestDocumentation.requestParameters(
                            RequestDocumentation.parameterWithName("page").description("페이지 번호").optional(),
                            RequestDocumentation.parameterWithName("size").description("페이지당 리스트 사이즈").optional(),
                            RequestDocumentation.parameterWithName("sort").description("페이지정렬, 예) 정확도 순: accuracy,desc").optional()
                        ),
                        PayloadDocumentation.responseFields(
                            PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success"),
                            PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("200"),
                            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("SUCCESS"),

                            PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.OBJECT).description("응답 데이터"),
                            PayloadDocumentation.fieldWithPath("content.page_info").type(JsonFieldType.OBJECT).description("페이지 관련 정보"),
                            PayloadDocumentation.fieldWithPath("content.page_info.page").type(JsonFieldType.NUMBER).description("페이지 정보"),
                            PayloadDocumentation.fieldWithPath("content.page_info.size").type(JsonFieldType.NUMBER).description("사이즈"),
                            PayloadDocumentation.fieldWithPath("content.page_info.sort").type(JsonFieldType.STRING).description("정렬 정보").optional(),

                            PayloadDocumentation.fieldWithPath("content.data").type(JsonFieldType.OBJECT).description("응답 데이터 정보"),
                            PayloadDocumentation.fieldWithPath("content.data.total_count").type(JsonFieldType.NUMBER).description("전체 결과 개수"),
                            PayloadDocumentation.fieldWithPath("content.data.pageable_count").type(JsonFieldType.NUMBER).description("totalCount 중에 노출 가능한 수").optional(),
                            PayloadDocumentation.fieldWithPath("content.data.end").type(JsonFieldType.BOOLEAN).description("현재 페이지가 마지막 페이지인지 여부").optional(),
                            PayloadDocumentation.fieldWithPath("content.data.channel_name").type(JsonFieldType.STRING).description("검색 결과를 제공한 채널 정보").optional(),

                            PayloadDocumentation.fieldWithPath("content.data.list").type(JsonFieldType.ARRAY).description("블로그 검색 목록 결과"),
                            PayloadDocumentation.fieldWithPath("content.data.list[].title").type(JsonFieldType.STRING).description("블로그 글 제목"),
                            PayloadDocumentation.fieldWithPath("content.data.list[].contents").type(JsonFieldType.STRING).description("블로그 글 요약"),
                            PayloadDocumentation.fieldWithPath("content.data.list[].url").type(JsonFieldType.STRING).description("블로그 글 URL").optional(),
                            PayloadDocumentation.fieldWithPath("content.data.list[].blogname").type(JsonFieldType.STRING).description("블로그의 이름"),
                            PayloadDocumentation.fieldWithPath("content.data.list[].thumbnail").type(JsonFieldType.STRING).description("미리보기 이미지 URL").optional(),
                            PayloadDocumentation.fieldWithPath("content.data.list[].datetime").type(JsonFieldType.STRING).description("블로그 글 작성시간"),
                        )
                    )
                )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
        }
    }


    @Nested
    inner class ApiGetPopularSearchKeywordList {

        @BeforeEach
        fun setup() {
            blogKeywordSetup.save()
            log.debug(":: StatisticsBlogKeywordHistory Data Setup Complete")
        }

        @Test
        @DisplayName("인기 검색어 조회 테스트")
        fun getPopularSearchKeywordList() {

            // when
            val resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/api/blog/popular/keyword")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andDo(MockMvcResultHandlers.print())
                .andDo(
                    MockMvcRestDocumentation.document(
                        "popular-keyword-list-get", Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        PayloadDocumentation.responseFields(
                            PayloadDocumentation.fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("success"),
                            PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("0"),
                            PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("SUCCESS"),

                            PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.OBJECT).description("응답 데이터"),
                            PayloadDocumentation.fieldWithPath("content.data").type(JsonFieldType.ARRAY).description("인기 검색어 목록 결과").optional(),
                            PayloadDocumentation.fieldWithPath("content.data[].keyword").type(JsonFieldType.STRING).description("블로그 검색 키워드").optional(),
                            PayloadDocumentation.fieldWithPath("content.data[].total").type(JsonFieldType.NUMBER).description("검색 횟수").optional(),
                        )
                    )
                )

            // then
            resultActions
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
        }

    }
}