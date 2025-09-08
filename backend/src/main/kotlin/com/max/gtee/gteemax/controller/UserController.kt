package com.max.gtee.gteemax.controller

import com.max.gtee.gteemax.entity.User
import com.max.gtee.gteemax.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class AuthRequestBody(
    val username: String,
    val password: String,
)

@RequestMapping("/user")
@RestController
class UserController(
    val service: UserService,
) {
    @GetMapping("/{id}")
    fun getUser(
        @PathVariable id: Int,
    ): User = service.getUser(id)

    @PostMapping("/login")
    fun login(
        @RequestBody request: AuthRequestBody,
    ): ResponseEntity<String> {
        val result = service.login(request.username, request.password)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody request: AuthRequestBody,
    ): ResponseEntity<User> {
        val result = service.register(request.username, request.password)
        return ResponseEntity.ok(result)
    }
}
