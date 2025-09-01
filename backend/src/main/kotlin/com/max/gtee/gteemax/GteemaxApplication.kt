package com.max.gtee.gteemax

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter

@SpringBootApplication
@ConfigurationPropertiesScan
class GteemaxApplication

fun main(args: Array<String>) {
    runApplication<GteemaxApplication>(*args)
}

@Configuration
class MvcDumpConfig(private val adapter: RequestMappingHandlerAdapter) {

    @PostConstruct
    fun dump() {
        println("HandlerAdapter bean = ${adapter.javaClass.name}")
        adapter.messageConverters.forEach { c ->
            println("MVC converter: ${c.javaClass.name}")
        }
    }
}