package com.max.gtee.gteemax.dto

import org.springframework.web.multipart.MultipartFile

data class UploadVideoDto(
    val creatorId: Int,
    val caption: String?,
    val videoFile: MultipartFile,
)