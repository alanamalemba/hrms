package com.suluhulabs.hrms.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class NotFoundException(message: String = "Not found") : ResponseStatusException(HttpStatus.NOT_FOUND, message)