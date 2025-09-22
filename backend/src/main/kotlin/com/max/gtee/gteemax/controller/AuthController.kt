package com.max.gtee.gteemax.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController {
    @GetMapping
    fun checkAuth(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authHeader: String,
    ): ResponseEntity<String> {
        val token = authHeader.removePrefix("Bearer ")
        return ResponseEntity.ok(token)
    }
}
