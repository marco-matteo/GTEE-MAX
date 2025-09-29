package com.max.gtee.gteemax.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
open class GteeException(m: String? = null, c: Throwable? = null) : Exception(m, c)