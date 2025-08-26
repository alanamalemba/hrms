package com.suluhulabs.hrms.exception

import com.suluhulabs.hrms.dto.ResponseBody
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {


    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(ex: ConflictException): ResponseEntity<ResponseBody<Nothing>?> {

        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ResponseBody(success = false, message = ex.message ?: "Conflict occurred"))


    }

    @ExceptionHandler(Exception::class)
    fun handleUnhandledException(ex: Exception): ResponseEntity<ResponseBody<Nothing>?> {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ResponseBody(success = false, message = ex.message ?: "Internal server error"))
    }

}