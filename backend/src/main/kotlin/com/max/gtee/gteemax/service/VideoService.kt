package com.max.gtee.gteemax.service

import com.max.gtee.gteemax.config.GteeConfig
import com.max.gtee.gteemax.dto.UploadVideoDto
import com.max.gtee.gteemax.dto.VideoDto
import com.max.gtee.gteemax.entity.Video
import com.max.gtee.gteemax.repository.FileRepository
import com.max.gtee.gteemax.repository.VideoRepository
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class VideoService (
    private val repository: VideoRepository,
    private val fileRepository: FileRepository,
    private val config: GteeConfig,
) {
        private val random: Random = config.seed?.let { Random(it) } ?: Random.Default

        fun uploadVideo(uploadVideo: UploadVideoDto): VideoDto {
        val video = repository.save(Video(
            caption = uploadVideo.caption,
            creator = uploadVideo.creatorId//User(uploadVideo.creatorId, "user", "", 0, null),
        ))

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

    fun deleteVideo(id: Int) {
        val video = repository.findById(id).get()
        fileRepository.delete(video)
        repository.deleteById(id)
    }
}