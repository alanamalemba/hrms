package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.ResponseBody
import com.suluhulabs.hrms.dto.SignUpRequestBody
import com.suluhulabs.hrms.dto.VerifyEmailRequestBody
import com.suluhulabs.hrms.service.AuthService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(val authService: AuthService, @param:Value("\${app.client.url}") val clientUrl: String) {

    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody signUpRequestBody: SignUpRequestBody): ResponseEntity<ResponseBody<Nothing>> {

        val successMessage = authService.signUp(signUpRequestBody)

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBody(message = successMessage, success = true))


    }

    @PostMapping("/verify-email")
    fun verifyEmail(@Valid @RequestBody verifyEmailRequestBody: VerifyEmailRequestBody): ResponseEntity<Void> {
        val successMessage = authService.verifyEmail(verifyEmailRequestBody)

        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "$clientUrl/sing-in").build()

    }


}