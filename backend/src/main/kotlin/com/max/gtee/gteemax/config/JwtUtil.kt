package com.max.gtee.gteemax.config

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.hibernate.boot.model.naming.IllegalIdentifierException
import org.springframework.stereotype.Component
import java.util.Date

const val EXPIRATION = 3600000

@Component
final class JwtUtil {
    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private val expirationMs = EXPIRATION

    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationMs)

        return Jwts
            .builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey)
            .compact()
    }

    fun getUsernameFromToken(token: String): String =
        Jwts
            .parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body.subject

    fun validateToken(token: String): Boolean =
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (_: ExpiredJwtException) {
            println("JWT token has expired")
            false
        } catch (_: JwtException) {
            println("Invalid JWT token")
            false
        } catch (_: IllegalIdentifierException) {
            println("Emptry or null JWT")
            false
        }
}
