package com.max.gtee.gteemax.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/auth")
@RestController
class AuthController {
    @GetMapping
    fun checkAuth(): String {
        val result = "You are Logged in!"
        return result
    }
}
