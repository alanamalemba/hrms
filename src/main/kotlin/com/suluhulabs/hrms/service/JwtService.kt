package com.suluhulabs.hrms.service

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

@Service
class JwtService(
    @param:Value("\${app.jwt.secret}") private val jwtSecret: String,
) {
    companion object {
         const val ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000L // 15 minutes
        private const val REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L // 7 days
        private const val VERIFICATION_TOKEN_EXPIRATION = 5 * 60 * 1000L// 5 minutes
        private const val CLAIM_TYPE = "type"
    }

    enum class TokenType(val value: String) { ACCESS("accessToken"), REFRESH("refreshToken"), VERIFICATION("verificationToken") }

    private val secretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray())

    private fun getExpiration(tokenType: TokenType) = when (tokenType) {
        TokenType.ACCESS -> ACCESS_TOKEN_EXPIRATION
        TokenType.REFRESH -> REFRESH_TOKEN_EXPIRATION
        TokenType.VERIFICATION -> VERIFICATION_TOKEN_EXPIRATION
    }

    private fun parseClaims(token: String) =
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload
        } catch (e: ExpiredJwtException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token expired")
        } catch (e: JwtException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token")
        }


    fun generateToken(userId: Long, tokenType: TokenType): String {
        val now = Date()
        val expiryDate = Date(now.time + getExpiration(tokenType))

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(expiryDate)
            .claim(CLAIM_TYPE, tokenType.name)
            .signWith(secretKey)
            .compact()
    }

    fun checkIsTokenValid(token: String, tokenType: TokenType): Boolean {
        try {
            val claims = parseClaims(token)

            return claims[CLAIM_TYPE] == tokenType.name

        } catch (e: Exception) {
            return false
        }
    }

    fun extractUserId(token: String, tokenType: TokenType): Long {
        val claims = parseClaims(token)

        if (claims[CLAIM_TYPE] != tokenType.name) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token")
        }

        return claims.subject.toLong()
    }
}
