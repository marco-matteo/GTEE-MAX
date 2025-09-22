package com.max.gtee.gteemax.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "gtee.video")
data class GteeConfig(
    val dir: String,
    val seed: Int?
)