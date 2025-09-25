package com.max.gtee.gteemax.controller

import com.max.gtee.gteemax.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController(
    private val jwtUtil: JwtUtil,
) {
    @GetMapping
    fun checkAuth(
        @CookieValue(name = "jwt", required = true) token: String,
    ): ResponseEntity<String> = ResponseEntity.ok("Authenticated! token: $token")

    @GetMapping("/user")
    fun getCurrentUser(
        @CookieValue(name = "jwt", required = true) token: String,
    ): ResponseEntity<String> = ResponseEntity.ok(jwtUtil.getUsernameFromToken(token))
}
