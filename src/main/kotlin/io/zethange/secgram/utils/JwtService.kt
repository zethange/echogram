package io.zethange.secgram.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.zethange.secgram.user.entities.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService {
    @Value("\${spring.security.token}")
    private lateinit var jwtSigningKey: String

    fun extractUsername(token: String): String {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey)))
            .build()
            .parseSignedClaims(token)
            .payload
            .subject
    }

    fun generateToken(user: User): String {
        return Jwts.builder()
            .subject(user.username)
            .claim("role", user.role)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3))
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey)))
            .compact()
    }

    fun isTokenValid(token: String): Boolean {
        val expiresAt = Jwts
            .parser()
            .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey)))
            .build()
            .parseSignedClaims(token)
            .payload.expiration

        return expiresAt != null && !expiresAt.before(Date())
    }
}