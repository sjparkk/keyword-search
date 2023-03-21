package com.keyword.keywordsearch.common.error

class CustomBlogSearchApisServerException: RuntimeException {

    var messageKey: String = "모든 채널의 블로그 검색 서버가 장애 발생"

    constructor(messageKey: String) : super(messageKey) {
        this.messageKey = messageKey
    }

    constructor() : super()
}