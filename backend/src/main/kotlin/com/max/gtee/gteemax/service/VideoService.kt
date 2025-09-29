package com.max.gtee.gteemax.service

import com.max.gtee.gteemax.config.GteeConfig
import com.max.gtee.gteemax.dto.UploadVideoDto
import com.max.gtee.gteemax.dto.VideoDto
import com.max.gtee.gteemax.entity.Video
import com.max.gtee.gteemax.exception.MissingPermissionException
import com.max.gtee.gteemax.repository.FileRepository
import com.max.gtee.gteemax.repository.VideoRepository
import com.max.gtee.gteemax.util.JwtUtil
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class VideoService(
    private val repository: VideoRepository,
    private val fileRepository: FileRepository,
    private val config: GteeConfig,
    private val userService: UserService,
    private val jwtUtil: JwtUtil,
) {
    private val random: Random = config.seed?.let { Random(it) } ?: Random.Default

    fun uploadVideo(
        uploadVideo: UploadVideoDto,
        token: String,
    ): VideoDto {
        checkIfOwnerOfVideo(uploadVideo.creatorId, token)

        val video =
            repository.save(
                Video(
                    caption = uploadVideo.caption,
                    creator = userService.getUser(uploadVideo.creatorId),
                ),
            )

        fileRepository.save(video, uploadVideo.videoFile)

        return repository.save(video).toDto()
    }

    fun getNextVideo(): VideoDto = repository.findAll().random(random).toDto()

    fun getVideo(id: Int): VideoDto = repository.findById(id).get().toDto()

    fun streamVideo(id: Int): Resource {
        val video = repository.findById(id).get()
        val file = fileRepository.find(video.path)

        video.copy(views = video.views + 1)
        repository.save(video)

        return FileSystemResource(file)
    }

    fun deleteVideo(
        id: Int,
        token: String,
    ) {
        val video = repository.findById(id).get()

        checkIfOwnerOfVideo(video.creator.username, token)

        fileRepository.delete(video)
        repository.deleteById(id)
    }

    fun checkIfOwnerOfVideo(
        videoOwnerId: String,
        token: String,
    ) {
        val requestUser = jwtUtil.getUsernameFromToken(token)
        if (videoOwnerId != requestUser) {
            throw MissingPermissionException("$requestUser has no permission to edit $videoOwnerId's videos")
        }
    }
}
