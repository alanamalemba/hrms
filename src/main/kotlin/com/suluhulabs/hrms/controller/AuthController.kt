package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.ResponseBody
import com.suluhulabs.hrms.dto.SignUpRequestBody
import com.suluhulabs.hrms.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(val authService: AuthService) {

    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody signUpRequestBody: SignUpRequestBody): ResponseEntity<ResponseBody<Nothing>> {

        val successMessage = authService.signUp(signUpRequestBody)

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBody(message = successMessage, success = true))


    }
}