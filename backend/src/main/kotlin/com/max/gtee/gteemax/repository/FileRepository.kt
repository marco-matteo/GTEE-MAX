package com.max.gtee.gteemax.repository

import com.max.gtee.gteemax.config.GteeConfig
import com.max.gtee.gteemax.entity.Video
import com.max.gtee.gteemax.exception.GteeException
import com.max.gtee.gteemax.exception.InvalidVideoException
import org.springframework.stereotype.Repository
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Path
import java.nio.file.Paths

@Repository
class FileRepository(
    config: GteeConfig,
) {
    private val videoDir = Paths.get(config.dir)

    fun save(
        video: Video,
        file: MultipartFile,
    ) {
        if (file.isEmpty) {
            throw InvalidVideoException("Received file is empty")
        }
        val path = videoDir.resolve(video.path)
        Files.createDirectories(path.parent)
        file.transferTo(path)
    }

    fun find(path: Path): File {
        val fullPath = videoDir.resolve(path)
        val file = fullPath.toFile()
        if (!file.exists()) {
            throw GteeException(c = FileNotFoundException("File not found: $fullPath"))
        }
        return file
    }

    fun delete(video: Video) {
        val path = videoDir.resolve(video.path)
        try {
            Files.delete(path)
        } catch (e: NoSuchFileException) {
            throw GteeException(c = e)
        }
    }
}
