package com.max.gtee.gteemax.repository

import com.max.gtee.gteemax.config.GteeConfig
import com.max.gtee.gteemax.entity.Video
import com.max.gtee.gteemax.exception.GteeException
import com.max.gtee.gteemax.exception.InvalidVideoException
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.test.assertEquals
import kotlin.test.assertFalse
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
        configMock = mock()
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
            .forEach(Files::deleteIfExists)
    }

    @Test
    fun `Video should be saved correctly`() {
        repository.save(videoMock, fileMock)

        val actual = File(expectedPath.toUri())
        assertTrue(actual.exists())
        assertEquals(expectedText, actual.readText(Charsets.UTF_8))
    }

    @Test
    fun `Throws when file is empty`() {
        val emptyFile = MockMultipartFile("empty.mp4", null)

        assertThrows<InvalidVideoException> { repository.save(videoMock, emptyFile)}
    }

    @Test
    fun `Saved File ist found correctly`() {
        repository.save(videoMock, fileMock)

        val expected = File(expectedPath.toUri())
        val actual = repository.find(videoMock.path).absoluteFile

        assertEquals(expected, actual)
    }

    @Test
    fun `Throws if file is not found`() {
        assertThrows<GteeException> {
            repository.find(Path("invalid"))
        }

    }

    @Test
    fun `Deletes videos correctly`() {
        repository.save(videoMock, fileMock)

        repository.delete(videoMock)

        assertFalse { File(expectedPath.toUri()).exists() }
    }

    @Test
    fun `Throws when video trying to be deleted does not exist`() {
        val invalidVideo: Video = mock()
        `when`(invalidVideo.path).thenReturn(Paths.get("invalid"))

        assertThrows<GteeException> { repository.delete(invalidVideo) }
    }
}