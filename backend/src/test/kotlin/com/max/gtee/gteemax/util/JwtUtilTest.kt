package com.max.gtee.gteemax.util

import io.jsonwebtoken.ExpiredJwtException
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtUtilTest {
    private lateinit var jwtUtil: JwtUtil
    private lateinit var jwtUtilExpired: JwtUtil
    private val username: String = "testuser"

    @BeforeAll
    fun setup() {
        jwtUtil = JwtUtil()
        jwtUtilExpired = JwtUtil(-1)
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun `Generate token from user name`() {
        val actualEncoded = jwtUtil.generateToken(username).split(".")

        assertTrue { actualEncoded.size == 3 }

        val actual =
            actualEncoded.dropLast(1)
                .map {
                    Base64.UrlSafe.decode(it).decodeToString()
                }

        val expectedAlg = """{"alg":"HS256"}"""

        assertEquals(expectedAlg, actual[0])
        assertTrue { actual[1].contains(""""sub":"testuser""") }
        assertTrue { actual[1].contains(""""iat":""") }
        assertTrue { actual[1].contains(""""exp":""") }
    }

    @Test
    fun `Get user name from token`() {
        val token = jwtUtil.generateToken(username)

        val actual = jwtUtil.getUsernameFromToken(token)

        assertEquals(username, actual)
    }

    @Test
    fun `Try to get user name from expired token`() {
        val token = jwtUtilExpired.generateToken(username)

        assertThrows<ExpiredJwtException> { jwtUtilExpired.getUsernameFromToken(token) }
    }

    @Test
    fun `Validate valid token`() {
        val token = jwtUtil.generateToken(username)

        assertTrue(jwtUtil.validateToken(token))
    }

    @Test
    fun `Validate expired token`() {
        val token = jwtUtilExpired.generateToken(username)
        val actual = assertDoesNotThrow { jwtUtilExpired.validateToken(token) }
        assertFalse { actual }
    }

    @Test
    fun `Validate invalid token`() {
        val token = jwtUtil.generateToken(username)
        val actual = assertDoesNotThrow { jwtUtilExpired.validateToken(token) }
        assertFalse { actual }
    }

    @Test
    fun `Validate non-token string`() {
        val token = "i'm no token"
        val actual = assertDoesNotThrow { jwtUtil.validateToken(token) }
        assertFalse { actual }
    }

    @Test
    fun `Validate empty string`() {
        val token = ""
        assertThrows<IllegalArgumentException> { jwtUtil.validateToken(token) }
    }
}
