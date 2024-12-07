package io.zethange.secgram.utils

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(value = [CustomException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleException(ex: CustomException): ResponseEntity<ExceptionDto<Any>> {
        return ResponseEntity.status(ex.httpStatus).body(ExceptionDto(ex.httpStatus.value(), ex.message))
    }


    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, List<String>>> {
        val error = mutableMapOf<String, List<String>>()

        for (fieldError in ex.bindingResult.fieldErrors) {

            if (error.contains(fieldError.field)) {
                error[fieldError.field]!!.plus(fieldError.defaultMessage)
            } else {
                error[fieldError.field] = mutableListOf(fieldError.defaultMessage.orEmpty())
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }
}