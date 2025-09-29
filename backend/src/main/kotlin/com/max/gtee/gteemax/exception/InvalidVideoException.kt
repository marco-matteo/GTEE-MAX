package com.max.gtee.gteemax.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
class InvalidVideoException(message: String? = null, cause: Throwable? = null) : GteeException(message, cause)
