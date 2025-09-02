package com.suluhulabs.hrms.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.suluhulabs.hrms.dto.ResponseBodyDto
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class RestAccessDeniedHandler(private val objectMapper: ObjectMapper) : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.contentType = "application/json"
        val body = ResponseBodyDto<Nothing>(success = false, message = accessDeniedException.message ?: "Forbidden")
        response.writer.write(objectMapper.writeValueAsString(body))
    }

}