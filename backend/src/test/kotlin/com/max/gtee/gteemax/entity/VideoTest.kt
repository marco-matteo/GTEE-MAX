package com.max.gtee.gteemax.entity

import com.max.gtee.gteemax.dto.VideoDto
import com.max.gtee.gteemax.exception.GteeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.nio.file.Paths

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VideoTest {
    private lateinit var userMock: User

    @BeforeAll
    fun setup() {
        userMock = mock()
        `when`(userMock.username).thenReturn("testuser")
    }

    @Test
    fun `Get path`() {
        val video = Video(id = 1, caption = "test", creator = userMock)
        val expected = Paths.get("testuser", "1.mp4")
        val actual = video.path
        assertEquals(expected, actual)
    }

    @Test
    fun `Throws when video id is null`() {
        val video = Video(caption = "test", creator = userMock)
        assertThrows<GteeException> { video.path }
    }

    @Test
    fun `Convert video to dto`() {
        val id = 1
        val views = 0
        val caption = "test"
        val creator = userMock

        val video = Video(id, views, caption, creator)
        val actual = video.toDto()

        val expected = VideoDto(id, caption, views, creator.username)

        assertEquals(expected, actual)
    }
}
