package com.max.gtee.gteemax.integration

import com.max.gtee.gteemax.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class UserTest {

    @Autowired
    lateinit var userService: UserService

    @Test
    fun `register user`() {
        val username = "testuser"
        val password = "pw"
        val createdUser = userService.register(username, password)
        assertEquals(username, createdUser.username)
    }
}