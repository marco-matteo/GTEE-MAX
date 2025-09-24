package com.max.gtee.gteemax

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class GteemaxApplication

fun main(args: Array<String>) {
    runApplication<GteemaxApplication>(*args)
}
