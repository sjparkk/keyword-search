package com.keyword.keywordsearch.controller.setup

import com.keyword.keywordsearch.domain.BlogKeyword
import com.keyword.keywordsearch.repository.BlogKeywordRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class BlogKeywordSetup(
    val blogKeywordRepository: BlogKeywordRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    //테스트에 사용 될 검색 키워드 목록
    val keywords = listOf("건강", "웰니스", "여행 목적지", "가정 장식", "육아 조언", "피트니스", "개인 금융",
        "은퇴 계획", "데이트", "레시피", "투자 전략", "패션 트렌드", "미용 제품", "결혼 계획", "보육 서비스", "온라인 과정",
        "시간 관리", "재택근무", "비즈니스", "애완 동물", "자기 개선", "기술 뉴스", "게임 팁", "주택 개조", "스포츠 뉴스",
        "자동차 리뷰", "음악", "영화", "레스토랑 리뷰", "서평", "가전 리뷰", "홈 보안", "관심사", "정신 건강", "자원봉사",
        "소셜 미디어", "온라인 포럼", "연예인", "뉴스 분석", "문화 행사", "정치 뉴스", "웹사이트", "세금", "온라인 뱅킹",
        "금융 서비스", "온라인 법률", "온라인 쇼핑", "집 청소", "아파트", "빌라")
    
    fun save() {

        log.debug(":: keywords size - ${keywords.size}")

        //100개의 Data 생성
        for(keyword in 1..100) {
            val blogKeyword = BlogKeyword()
            //RandomNumberTest를 통해 랜덤 난수 검증
            blogKeyword.keyword = keywords[Random.nextInt(keywords.size)]

            blogKeywordRepository.save(blogKeyword)
        }
    }
}

