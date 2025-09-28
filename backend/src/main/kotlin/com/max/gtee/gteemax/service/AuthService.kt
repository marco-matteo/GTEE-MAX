package com.max.gtee.gteemax.service

import com.max.gtee.gteemax.repository.UserRepository
import org.springframework.dao.DataAccessException
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
) {
    fun checkUserExists(username: String): Boolean =
        try {
            val user = userRepository.findById(username).orElse(null)
            user != null
        } catch (ex: DataAccessException) {
            println("Database error while checking user existence: ${ex.message}")
            false
        }
}
