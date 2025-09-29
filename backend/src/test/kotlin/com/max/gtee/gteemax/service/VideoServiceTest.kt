package com.max.gtee.gteemax.service

import com.max.gtee.gteemax.config.GteeConfig
import com.max.gtee.gteemax.dto.UploadVideoDto
import com.max.gtee.gteemax.entity.User
import com.max.gtee.gteemax.entity.Video
import com.max.gtee.gteemax.exception.MissingPermissionException
import com.max.gtee.gteemax.repository.FileRepository
import com.max.gtee.gteemax.repository.VideoRepository
import com.max.gtee.gteemax.util.JwtUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.`when`
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.util.Optional

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VideoServiceTest {
    lateinit var videoService: VideoService
    lateinit var videoRepository: VideoRepository
    lateinit var fileRepository: FileRepository
    lateinit var gteeConfig: GteeConfig
    lateinit var userService: UserService
    lateinit var jwtUtil: JwtUtil
    lateinit var uploadVideo: UploadVideoDto

    val userName = "testuser"


    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @BeforeEach
    fun setup() {
        val testUser = User("testuser", "pw")
        val testVideo = Video(id = 1, caption = "", creator = testUser)
        videoRepository = mock()
        `when`(videoRepository.save(any(Video::class.java))).thenReturn(testVideo)
        `when`(videoRepository.findById(any(Int::class.java))).thenReturn(Optional.of(testVideo))
        fileRepository = mock()
        gteeConfig = GteeConfig("", 21)
        userService = mock()
        `when`(userService.getUser("testuser")).thenReturn(testUser)
        jwtUtil = JwtUtil()
        videoService = VideoService(
            videoRepository,
            fileRepository,
            gteeConfig,
            userService,
            jwtUtil
        )
        uploadVideo = UploadVideoDto(
            "testuser",
            "test",
            MockMultipartFile("testfile.mp4", "testcontent".toByteArray())
        )
    }

    @Test
    fun `Upload video`() {
        val token = jwtUtil.generateToken(userName)
        videoService.uploadVideo(uploadVideo, token)
        verify(fileRepository).save(any(Video::class.java), any(MultipartFile::class.java))
        verify(videoRepository, times(2)).save(any(Video::class.java))

    }

    @Test
    fun `Try to upload video for different user`() {
        val token = jwtUtil.generateToken("other user")
        assertThrows<MissingPermissionException> { videoService.uploadVideo(uploadVideo, token) }
        verifyNoInteractions(fileRepository)
        verifyNoInteractions(videoRepository)
    }

    @Test
    fun `Delete video`() {
        val token = jwtUtil.generateToken(userName)
        videoService.deleteVideo(1, token)
        verify(videoRepository).findById(1)
        verify(fileRepository).delete(any(Video::class.java))
        verify(videoRepository).deleteById(1)
    }

    @Test
    fun `Try to delete video for different user`() {
        val token = jwtUtil.generateToken("other user")
        assertThrows<MissingPermissionException> { videoService.deleteVideo(1, token) }
        verifyNoInteractions(fileRepository)
        verify(videoRepository, times(0)).deleteById(1)
    }

    @Test
    fun `Check if owner is owner of video`() {
        val token = jwtUtil.generateToken(userName)
        assertDoesNotThrow { videoService.checkIfOwnerOfVideo(userName, token) }
    }

    @Test
    fun `Check if other person is owner of video`() {
        val token = jwtUtil.generateToken("other user")
        assertThrows<MissingPermissionException> { videoService.checkIfOwnerOfVideo(userName, token) }
    }
}