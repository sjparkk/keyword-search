package com.keyword.keywordsearch.common.error.handler

import com.keyword.keywordsearch.common.dto.CommonResponse
import com.keyword.keywordsearch.common.error.*
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Exception handler
 */
@RestControllerAdvice
class ExceptionHandler(
    val messageSource: MessageSource
) {

    @ExceptionHandler(CustomInternalServerException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun internalServerException(e: CustomInternalServerException): CommonResponse<Any> {
        return CommonResponse(
            errorCode = Integer.valueOf(getMessage(e.messageKey + ".code")),
            message = getMessage(e.messageKey + ".message")
        )
    }

    @ExceptionHandler(CustomUnauthorizedException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun unauthorizedException(e: CustomUnauthorizedException): CommonResponse<Any> {
        return CommonResponse(
            errorCode = Integer.valueOf(getMessage(e.messageKey + ".code")),
            message = getMessage(e.messageKey + ".message")
        )
    }

    @ExceptionHandler(CustomBadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun badRequestException(e: CustomBadRequestException): CommonResponse<Any> {
        return CommonResponse(
            errorCode = Integer.valueOf(getMessage(e.messageKey + ".code")),
            message = getMessage(e.messageKey + ".message")
        )
    }

    @ExceptionHandler(CustomBlogSearchApisServerException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun customBlogSearchApisServerException(e: CustomBlogSearchApisServerException): CommonResponse<Any> {
        return CommonResponse(
            errorCode = Integer.valueOf(getMessage(e.messageKey + ".code")),
            message = getMessage(e.messageKey + ".message")
        )
    }

    @ExceptionHandler(CustomNotFoundPopularKeyword::class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun customNotFoundPopularKeyword(e: CustomNotFoundPopularKeyword): CommonResponse<Any> {
        return CommonResponse(
            errorCode = Integer.valueOf(getMessage(e.messageKey + ".code")),
            message = getMessage(e.messageKey + ".message")
        )
    }

    private fun getMessage(code: String): String {
        return getMessage(code, null)
    }

    private fun getMessage(code: String, args: Array<Any>?): String {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale())
    }

}