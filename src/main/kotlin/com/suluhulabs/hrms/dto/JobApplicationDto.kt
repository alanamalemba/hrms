package com.suluhulabs.hrms.dto

import com.suluhulabs.hrms.model.JobApplication
import java.time.LocalDateTime

data class JobApplicationDto (
    val id:Long?,
    val user: UserDto,
    val jobPosting: JobPostingDto,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val status: JobApplication.Status
)
