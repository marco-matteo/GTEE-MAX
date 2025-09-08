package com.max.gtee.gteemax.service

import com.max.gtee.gteemax.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    val repository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByUsername(username)
        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            emptyList(),
        )
    }
}
