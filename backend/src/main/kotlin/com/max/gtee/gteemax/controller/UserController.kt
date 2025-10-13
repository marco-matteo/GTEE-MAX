package com.max.gtee.gteemax.controller

import com.max.gtee.gteemax.dto.AuthRequestBody
import com.max.gtee.gteemax.entity.User
import com.max.gtee.gteemax.service.UserService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user")
@RestController
class UserController(
    val service: UserService,
) {
    @GetMapping("/{username}")
    fun getUser(
        @PathVariable username: String,
    ): User = service.getUser(username)

    @PostMapping("/login")
    fun login(
        @RequestBody request: AuthRequestBody,
        response: HttpServletResponse,
    ): ResponseEntity<String> {
        val token = service.login(request.username, request.password)

        val cookie =
            Cookie("jwt", token).apply {
                isHttpOnly = true
                secure = false
                path = "/"
            }

        response.addCookie(cookie)
        return ResponseEntity.ok("Login successful")
    }

    @PostMapping("/register")
    fun register(
        @RequestBody request: AuthRequestBody,
    ): ResponseEntity<User> {
        val result = service.register(request.username, request.password)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/cleanup")
    fun cleanup(): ResponseEntity<String> {
        service.cleanup()
        return ResponseEntity.ok("Deleted Test User")
    }
}
