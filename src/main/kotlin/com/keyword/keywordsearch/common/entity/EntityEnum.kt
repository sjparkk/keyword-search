package com.keyword.keywordsearch.common.entity

class EntityEnum {

    /**
     * 키워드 블로그 검색 API 시도할 채널 정보
     */
    enum class SearchChannelType(val value: String, val desc: String){
        KAKAO("KAKAO", "카카오"),
        NAVER("NAVER", "네이버")
    }

}