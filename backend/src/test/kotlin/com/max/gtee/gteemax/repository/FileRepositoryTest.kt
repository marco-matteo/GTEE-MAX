package com.max.gtee.gteemax.repository

import com.max.gtee.gteemax.config.GteeConfig
import com.max.gtee.gteemax.entity.Video
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.io.TempDir
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.absolute
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileRepositoryTest() {
    val testDir: String = "_test-video"

    lateinit var configMock: GteeConfig
    lateinit var repository: FileRepository
    lateinit var videoMock: Video
    lateinit var expectedPath: Path
    lateinit var fileMock: MultipartFile
    lateinit var expectedText: String

    @BeforeAll
    fun setup() {
        configMock = mock(GteeConfig::class.java)
        `when`(configMock.dir).thenReturn(testDir)
        videoMock = mock()
        `when`(videoMock.path).thenReturn(Paths.get("test-user", "video.mp4"))
        repository = FileRepository(configMock)
        expectedText = "Test"
        expectedPath = Paths.get(configMock.dir, videoMock.path.toString())
        fileMock = MockMultipartFile("video.mp4", expectedText.toByteArray(Charsets.UTF_8))
    }

    @AfterAll
    fun cleanup() {
        Files.walk(Path( testDir))
            .sorted(Comparator.reverseOrder())   // delete children first
            .forEach(Files::delete)
    }

    @Test
    fun `Video should be saved correctly`() {
        repository.save(videoMock, fileMock)

        val actual = File(expectedPath.toUri())
        assertTrue(actual.exists())
        assertEquals(expectedText, actual.readText(Charsets.UTF_8))
    }

    @Test
    fun `Saved File ist found correctly`() {
        repository.save(videoMock, fileMock)

        val expected = File(expectedPath.toUri())
        val actual = repository.find(videoMock.path.absolute())

        assertEquals(expected, actual)
    }
}