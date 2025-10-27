package com.max.gtee.gteemax.repository

import com.max.gtee.gteemax.entity.Video
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VideoRepository : JpaRepository<Video, Int> {
    fun findAllByCreatorUsername(username: String): List<Video>
}
