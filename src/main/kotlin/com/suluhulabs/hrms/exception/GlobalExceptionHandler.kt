package com.suluhulabs.hrms.exception

import com.suluhulabs.hrms.dto.ResponseBodyDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatus(ex: ResponseStatusException): ResponseEntity<ResponseBodyDto<Nothing>> {
        logger.error(ex.message, ex)

        return ResponseEntity
            .status(ex.statusCode)
            .body(ResponseBodyDto(success = false, message = ex.reason ?: "Request failed"))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnhandledException(ex: Exception): ResponseEntity<ResponseBodyDto<Nothing>?> {

        logger.error(ex.message, ex)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseBodyDto(success = false, message = "Internal server error"))
    }

}