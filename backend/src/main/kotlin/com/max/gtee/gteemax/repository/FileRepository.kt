package com.max.gtee.gteemax.repository

import com.max.gtee.gteemax.config.GteeConfig
import com.max.gtee.gteemax.entity.Video
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class FileRepository(
    config: GteeConfig
){
    private val videoDir = Paths.get(config.dir)

    fun save(video: Video, file: MultipartFile) {
        val path = videoDir.resolve(video.path)
        Files.createDirectories(path.parent)
        file.transferTo(path)
    }

    fun find(path: Path): File {
        val fullPath = videoDir.resolve(path)
        return fullPath.toFile()
    }

    fun delete(video: Video) {
        val path = videoDir.resolve(video.path)
        Files.delete(path)
    }
}