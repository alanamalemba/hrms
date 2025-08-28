package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.ResponseBody
import com.suluhulabs.hrms.dto.SignInRequestBody
import com.suluhulabs.hrms.dto.SignInResponseDto
import com.suluhulabs.hrms.dto.SignUpRequestBody
import com.suluhulabs.hrms.dto.VerifyEmailRequestBody
import com.suluhulabs.hrms.mapper.toUserDto
import com.suluhulabs.hrms.service.AuthService
import com.suluhulabs.hrms.service.JwtService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
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
    fun signUp(@Valid @RequestBody signUpRequestBody: SignUpRequestBody): ResponseEntity<ResponseBody<Unit>> {

        val successMessage = authService.signUp(signUpRequestBody)

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBody(message = successMessage, success = true))


    }

    @PostMapping("/verify-email")
    fun verifyEmail(@Valid @RequestBody verifyEmailRequestBody: VerifyEmailRequestBody): ResponseEntity<Unit> {
        authService.verifyEmail(verifyEmailRequestBody)

        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "$clientUrl/sing-in").build()

    }

    @PostMapping("/sign-in")
    fun signIn(
        @Valid @RequestBody signInRequestBody: SignInRequestBody, httpServletResponse: HttpServletResponse
    ): ResponseEntity<ResponseBody<SignInResponseDto>?> {

        val (accessToken, refreshToken, targetUser) = authService.signIn(signInRequestBody)

        val cookie = Cookie(JwtService.TokenType.ACCESS.value, accessToken).apply {
            isHttpOnly = true
            path = "/"
            maxAge = JwtService.ACCESS_TOKEN_EXPIRATION.toInt()
        }

        httpServletResponse.addCookie(cookie)

        return ResponseEntity.ok().body(
            ResponseBody(
                success = true,
                message = "Logged in successfully",
                data = SignInResponseDto(user = targetUser.toUserDto(), accessToken = accessToken)
            )
        )

    }


}