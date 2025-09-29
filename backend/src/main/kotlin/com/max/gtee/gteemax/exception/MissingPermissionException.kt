package com.max.gtee.gteemax.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class MissingPermissionException(message: String? = null, cause: Throwable? = null) : GteeException(message, cause)
