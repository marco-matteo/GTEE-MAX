package com.max.gtee.gteemax.service

import com.max.gtee.gteemax.config.GteeConfig
import com.max.gtee.gteemax.dto.UploadVideoDto
import com.max.gtee.gteemax.dto.VideoDto
import com.max.gtee.gteemax.entity.User
import com.max.gtee.gteemax.entity.Video
import com.max.gtee.gteemax.exception.MissingPermissionException
import com.max.gtee.gteemax.exception.VideoNotFoundException
import com.max.gtee.gteemax.repository.FileRepository
import com.max.gtee.gteemax.repository.VideoRepository
import com.max.gtee.gteemax.util.JwtUtil
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertNull
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.util.Optional
import kotlin.io.path.Path
import kotlin.test.assertEquals

class VideoServiceTest {
    @Nested
    inner class GeneralTest {
        private lateinit var videoService: VideoService
        private lateinit var videoRepository: VideoRepository
        private lateinit var fileRepository: FileRepository
        private lateinit var gteeConfig: GteeConfig
        private lateinit var userService: UserService
        private lateinit var jwtUtil: JwtUtil
        private lateinit var uploadVideo: UploadVideoDto

        private val userName = "testuser"

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
            videoService =
                VideoService(
                    videoRepository,
                    fileRepository,
                    mock(),
                    gteeConfig,
                    userService,
                    jwtUtil,
                )
            uploadVideo =
                UploadVideoDto(
                    "testuser",
                    "test",
                    MockMultipartFile("testfile.mp4", "testcontent".toByteArray()),
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
            verify(videoRepository, never()).deleteById(1)
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

    @Nested
    @SpringBootTest
    inner class FavoriteTest(
        @param:Autowired val subject: VideoService,
        @param:Autowired val userService: UserService,
        @param:Autowired val config: GteeConfig,
    ) {
        lateinit var user: User
        lateinit var video: VideoDto
        lateinit var video2: VideoDto
        lateinit var jwtUtil: JwtUtil
        lateinit var token: String

        @BeforeEach
        fun setup() {
            val username = "testy"
            val pw = "pw"
            jwtUtil = JwtUtil()
            user = userService.register(username, pw)
            token = userService.login(username, pw)
            video =
                subject.uploadVideo(
                    UploadVideoDto(
                        user.username,
                        "",
                        MockMultipartFile("test.mp4", byteArrayOf(1.toByte())),
                    ),
                    token,
                )
            video2 =
                subject.uploadVideo(
                    UploadVideoDto(
                        user.username,
                        "",
                        MockMultipartFile("test2.mp4", byteArrayOf(1.toByte())),
                    ),
                    token,
                )
        }

        @AfterEach
        fun cleanUp() {
            Files.walk(Path(config.dir))
                .sorted(Comparator.reverseOrder()) // delete children first
                .forEach(Files::deleteIfExists)
        }

        @Test
        fun `User can favorite a video`() {
            subject.favoriteVideo(video.id, token)

            val actual = userService.getUser(user.username).favorite?.toDto()
            assertEquals(video, actual)
        }

        @Test
        fun `Throws when id is invalid`() {
            assertThrows<VideoNotFoundException> { subject.favoriteVideo(-1, token) }
        }

        @Test
        fun `Favorite video can be overwritten`() {
            subject.favoriteVideo(video.id, token)
            val first = userService.getUser(user.username).favorite?.toDto()
            assertEquals(video, first)

            subject.favoriteVideo(video2.id, token)
            val actual = userService.getUser(user.username).favorite?.toDto()
            assertEquals(video2, actual)
        }

        @Test
        fun `User can unfavorite video`() {
            subject.favoriteVideo(video.id, token)
            val first = userService.getUser(user.username).favorite?.toDto()
            assertEquals(video, first)

            subject.unfavoriteVideo(token)
            val actual = userService.getUser(user.username).favorite
            assertNull(actual)
        }

        @Test
        fun `Does not throw when user does not have a favorite video`() {
            assertDoesNotThrow { subject.unfavoriteVideo(token) }
        }
    }
}
