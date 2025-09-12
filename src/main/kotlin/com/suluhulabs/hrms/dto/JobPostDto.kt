package com.suluhulabs.hrms.dto

import java.time.LocalDateTime

data class JobPostingDto(
    val id: Long? = null,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)
