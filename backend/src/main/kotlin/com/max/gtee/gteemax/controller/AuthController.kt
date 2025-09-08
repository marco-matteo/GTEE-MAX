package com.max.gtee.gteemax.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController {
    @GetMapping
    fun checkAuth(request: HttpServletRequest): ResponseEntity<String> {
        val authHeader = request.getHeader("Authorization")
        val token = authHeader?.removePrefix("Bearer ")
        return ResponseEntity.ok(token)
    }
}
