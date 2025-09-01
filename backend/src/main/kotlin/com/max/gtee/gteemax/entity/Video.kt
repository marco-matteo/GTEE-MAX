package com.max.gtee.gteemax.entity

import com.max.gtee.gteemax.dto.VideoDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import java.nio.file.Path
import java.nio.file.Paths

@Entity
data class Video(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,
    val views: Int = 0,
    val caption: String?,
//    @OneToOne
    val creator: Int,
) {
    val path: Path
            get() = Paths.get(creator.toString(), "${id}.mp4")
    fun toDto(): VideoDto = VideoDto(
        id = id!!,
        caption = caption,
        views = views,
        creatorId = creator
    )
}
