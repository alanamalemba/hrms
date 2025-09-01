package com.suluhulabs.hrms.dto

import java.time.LocalDate
import java.time.LocalDateTime

data class ProfileDto(
    val id: Long? = null,
    val profession: String?,
    val birthDate: LocalDate?,
    val createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
)