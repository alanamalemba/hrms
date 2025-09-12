package com.suluhulabs.hrms.dto

import java.time.LocalDateTime

data class OrganizationDto(
    val id: Long? = null,
    val name: String,
    val createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
)