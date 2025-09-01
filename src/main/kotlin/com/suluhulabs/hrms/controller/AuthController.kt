package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.ResponseBody
import com.suluhulabs.hrms.dto.SignInRequestDto
import com.suluhulabs.hrms.dto.SignInResponseDto
import com.suluhulabs.hrms.dto.SignUpRequestDto
import com.suluhulabs.hrms.dto.VerifyEmailRequestDto
import com.suluhulabs.hrms.mapper.toUserDto
import com.suluhulabs.hrms.service.AuthService
import com.suluhulabs.hrms.service.JwtService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(val authService: AuthService, @param:Value("\${app.client.url}") val clientUrl: String) {

    @PostMapping("/sign-up")
    fun signUp(@Valid @RequestBody signUpRequestDto: SignUpRequestDto): ResponseEntity<ResponseBody<Unit>> {

        val successMessage = authService.signUp(signUpRequestDto)

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseBody(message = successMessage, success = true))


    }

    @PostMapping("/verify-email")
    fun verifyEmail(@Valid @RequestBody verifyEmailRequestDto: VerifyEmailRequestDto): ResponseEntity<Unit> {
        authService.verifyEmail(verifyEmailRequestDto)

        return ResponseEntity.status(HttpStatus.FOUND).header("Location", "$clientUrl/sing-in").build()

    }

    @PostMapping("/sign-in")
    fun signIn(
        @Valid @RequestBody signInRequestDto: SignInRequestDto, httpServletResponse: HttpServletResponse
    ): ResponseEntity<ResponseBody<SignInResponseDto>?> {

        val (accessToken, refreshToken, targetUser) = authService.signIn(signInRequestDto)

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