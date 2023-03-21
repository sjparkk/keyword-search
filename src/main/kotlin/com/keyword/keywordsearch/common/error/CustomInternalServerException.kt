package com.keyword.keywordsearch.common.error

class CustomInternalServerException: RuntimeException {

    var messageKey: String? = null

    constructor(messageKey: String?) : super(messageKey) {
        this.messageKey = messageKey
    }

    constructor() : super()
}