package com.max.gtee.gteemax.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController {
    @GetMapping
    fun checkAuth(
        @CookieValue(name = "jwt", required = false) token: String,
    ): ResponseEntity<String> = ResponseEntity.ok("Authenticated! token: $token")
}
