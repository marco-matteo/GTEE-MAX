package com.max.gtee.gteemax.entity

import com.max.gtee.gteemax.dto.VideoDto
import com.max.gtee.gteemax.exception.GteeException
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.nio.file.Path
import java.nio.file.Paths

@Entity
data class Video(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,
    val views: Int = 0,
    val caption: String,
    @ManyToOne
    val creator: User,
) {
    val path: Path
        get() {
            if (id == null) {
                throw GteeException(
                    "Cannot generate video path because the videos id is null",
                    NullPointerException("id is null")
                )
            }
            return Paths.get(creator.username, "$id.mp4")
        }

    fun toDto(): VideoDto =
        VideoDto(
            id = id!!,
            caption = caption,
            views = views,
            creatorId = creator.username,
        )
}
