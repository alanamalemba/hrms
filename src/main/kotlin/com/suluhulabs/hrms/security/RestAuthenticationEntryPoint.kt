package com.suluhulabs.hrms.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.suluhulabs.hrms.dto.ResponseBody
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class RestAuthenticationEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        val body = ResponseBody<Nothing>(success = false, message = authException.message ?: "Unauthorized")
        response.writer.write(objectMapper.writeValueAsString(body))
    }
}