package com.suluhulabs.hrms.security

import com.suluhulabs.hrms.service.JwtService
import com.suluhulabs.hrms.service.UserDetailsServiceImpl
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(private val jwtService: JwtService, val userDetailsServiceImpl: UserDetailsServiceImpl) :
    OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        // If no token is provided, skip and let Security rules apply
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val accessToken = authHeader.removePrefix("Bearer ")

        if (!jwtService.checkIsTokenValid(accessToken, JwtService.TokenType.ACCESS)) throw Exception(
            "Invalid credentials"
        )

        val userId = jwtService.extractUserId(accessToken, JwtService.TokenType.ACCESS)

        val userDetails = userDetailsServiceImpl.loadUserById(userId)
            ?: throw java.lang.Exception("Invalid credentials")

        val auth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

        SecurityContextHolder.getContext().authentication = auth

        filterChain.doFilter(request, response)
    }
}