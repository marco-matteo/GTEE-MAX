package com.max.gtee.gteemax.integration

import com.max.gtee.gteemax.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.assertEquals

@SpringBootTest
@AutoConfigureMockMvc
class User @Autowired constructor(
    private val mockMvc: MockMvc,
    private val userService: UserService
) {

    @Test
    fun `register username`() {
        val json = """{"username":"sandro","password":"password"}"""

        mockMvc.perform(
            post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json),
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.username").value("sandro"))
    }

    @Test
    fun `register user`() {
        val username = "testuser"
        val password = "pw"
        val createdUser = userService.register(username, password)
        assertEquals(username, createdUser.username)
    }
}
