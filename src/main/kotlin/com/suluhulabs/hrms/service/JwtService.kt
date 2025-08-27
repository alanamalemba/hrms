import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService(
    @param:Value("\${app.jwt.access-token-secret}") private val accessTokenSecret: String,
    @param:Value("\${app.jwt.refresh-token-secret}") private val refreshTokenSecret: String
) {

    companion object {
        private const val ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000L // 15 minutes
        private const val REFRESH_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L // 7 days

    }

    enum class TokenType { ACCESS, REFRESH }

    private fun getKey(tokenType: TokenType): SecretKey =
        Keys.hmacShaKeyFor(
            if (tokenType == TokenType.ACCESS) accessTokenSecret.toByteArray()
            else refreshTokenSecret.toByteArray()
        )

    fun generateToken(userId: Long, tokenType: TokenType): String {
        val expiration = if (tokenType == TokenType.ACCESS) ACCESS_TOKEN_EXPIRATION else REFRESH_TOKEN_EXPIRATION
        val now = Date()
        val expiryDate = Date(now.time + expiration)

        return Jwts.builder()
            .subject(userId.toString())
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(getKey(tokenType))
            .compact()
    }

    fun checkIsTokenValid(token: String, tokenType: TokenType): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(getKey(tokenType))
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun extractUserId(token: String, tokenType: TokenType): String {
        return Jwts.parser()
            .verifyWith(getKey(tokenType))
            .build()
            .parseSignedClaims(token).payload.subject
    }
}
