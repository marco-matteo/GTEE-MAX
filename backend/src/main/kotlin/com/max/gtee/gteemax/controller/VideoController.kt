package com.max.gtee.gteemax.controller

import com.max.gtee.gteemax.dto.UploadVideoDto
import com.max.gtee.gteemax.dto.VideoDto
import com.max.gtee.gteemax.service.VideoService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.MediaTypeFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/video")
@CrossOrigin
class VideoController(
    private val service: VideoService,
) {
    @GetMapping("/{id}")
    fun getVideo(@PathVariable id: Int): VideoDto = service.getVideo(id)

    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun uploadVideo(@ModelAttribute video: UploadVideoDto): VideoDto = service.uploadVideo(video)

    @GetMapping("/stream/{id}")
    fun streamVideo(@PathVariable id: Int): ResponseEntity<Resource> {
        val resource = service.streamVideo(id)
        val mediaType = MediaTypeFactory.getMediaType(resource)
            .orElse(MediaType("video", "mp4"))

        return ResponseEntity.ok()
            .contentType(mediaType)
            .header(HttpHeaders.ACCEPT_RANGES, "bytes")
            .body(resource)
    }

    @GetMapping("/next")
    fun getNextVideo(): VideoDto = service.getNextVideo()

    @DeleteMapping("/{id}")
    fun deleteVideo(@PathVariable id: Int) = service.deleteVideo(id)
}