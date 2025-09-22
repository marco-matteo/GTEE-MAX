package com.max.gtee.gteemax.dto

import org.springframework.web.multipart.MultipartFile

data class UploadVideoDto(
    val creatorId: String,
    val caption: String?,
    val videoFile: MultipartFile,
)
