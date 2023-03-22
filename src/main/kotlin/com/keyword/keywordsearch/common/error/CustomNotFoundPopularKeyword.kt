package com.keyword.keywordsearch.common.error

class CustomNotFoundPopularKeyword: RuntimeException {

    var messageKey: String = "인기 키워드 검색 결과가 존재하지 않습니다"

    constructor(messageKey: String) : super(messageKey) {
        this.messageKey = messageKey
    }

    constructor() : super()
}