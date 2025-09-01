package com.max.gtee.gteemax.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "gtee")
data class GteeConfig(
    val videoDir: String,
)