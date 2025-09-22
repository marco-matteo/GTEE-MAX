package com.max.gtee.gteemax.service

import com.max.gtee.gteemax.config.JwtUtil
import com.max.gtee.gteemax.config.SecurityConfig
import com.max.gtee.gteemax.entity.User
import com.max.gtee.gteemax.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService(
    val repository: UserRepository,
    val authenticationManager: AuthenticationManager,
    val jwtUtil: JwtUtil,
    private val security: SecurityConfig,
) {
    fun getUser(username: String): User = repository.findById(username).get()

    fun login(
        username: String,
        password: String,
    ): String {
        val authentication =
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(username, password),
            )
        SecurityContextHolder.getContext().authentication = authentication
        return jwtUtil.generateToken(username)
    }

    fun register(
        username: String,
        password: String,
    ): User {
        val encryptedPassword = security.passwordEncoder().encode(password)
        val newUser = User(username = username, password = encryptedPassword)

        return repository.save(newUser)
    }
}
