package com.max.gtee.gteemax.service

import com.max.gtee.gteemax.entity.User
import com.max.gtee.gteemax.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class UserService (
    val repository: UserRepository,
    val authenticationManager: AuthenticationManager
) {

    fun getUser(id: Int): User = repository.findById(id).get()

    fun login(username: String, password: String): String {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        return "Login erfolgreich für User: $username"
    }






}