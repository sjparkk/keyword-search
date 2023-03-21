package com.keyword.keywordsearch.common.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus

@Schema(title = "공통 응답")
class CommonResponse<T>(
    @field:Schema(title = "응답 코드", example = "200")
    val code: Int,
    @field:Schema(title = "응답 메세지", example = "SUCCESS")
    val message: String,
    @field:Schema(title = "응답 데이터")
    var content: T?,
    @field:Schema(title = "성공 여부")
    var success: Boolean,
) {

    constructor(content: T) : this(HttpStatus.OK.value(), HttpStatus.OK.reasonPhrase, content, true)

    constructor(httpStatus: HttpStatus, content: T) : this(httpStatus.value(), httpStatus.reasonPhrase, content, true)

    constructor(errorCode: Int, message: String) : this(errorCode, message, null, false)

    constructor(errorCode: Int, message: String, content: T) : this(errorCode, message, content, false)

    override fun toString(): String {
        return "CommonResponse(code=$code, message='$message', content=$content, success=$success)"
    }
}