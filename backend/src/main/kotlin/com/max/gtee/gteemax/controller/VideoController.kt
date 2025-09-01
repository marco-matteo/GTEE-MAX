package com.max.gtee.gteemax.controller

import com.max.gtee.gteemax.dto.UploadVideoDto
import com.max.gtee.gteemax.dto.VideoDto
import com.max.gtee.gteemax.service.VideoService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRange
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.MediaTypeFactory
import org.springframework.http.ResponseEntity
import org.springframework.core.io.support.ResourceRegion
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin


@RestController
@RequestMapping("/video")
@CrossOrigin
class VideoController(
    private val service: VideoService,
) {
    private val oneMb = 1024 * 1024
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
}