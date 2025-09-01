package com.max.gtee.gteemax.service

import com.max.gtee.gteemax.config.GteeConfig
import com.max.gtee.gteemax.dto.UploadVideoDto
import com.max.gtee.gteemax.dto.VideoDto
import com.max.gtee.gteemax.entity.User
import com.max.gtee.gteemax.entity.Video
import com.max.gtee.gteemax.repository.VideoRepository
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.File

@Service
class VideoService (
    private val repository: VideoRepository,
    private val fileService: FileService,
    private val config: GteeConfig
) {
    fun uploadVideo(uploadVideo: UploadVideoDto): VideoDto {
        val video = repository.save(Video(
            caption = uploadVideo.caption,
            creator = uploadVideo.creatorId//User(uploadVideo.creatorId, "user", "", 0, null),
        ))

        fileService.save(video, uploadVideo.videoFile)

        return repository.save(video).toDto()
    }

    fun getVideo(id: Int): VideoDto = repository.findById(id).get().toDto()

    fun streamVideo(id: Int): Resource {
        val video = repository.findById(id).get()
        val file = fileService.find(video.path)
        return FileSystemResource(file)
    }
}