package io.zethange.secgram.utils

import org.springframework.http.HttpStatus

class CustomException(
    val httpStatus: HttpStatus,
    override val message: String,
) : Exception(message)

data class ExceptionDto<T>(
    val httpStatus: Int,
    val message: String,
    val data: T? = null,
)