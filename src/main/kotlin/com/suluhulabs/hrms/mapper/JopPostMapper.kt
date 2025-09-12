package com.suluhulabs.hrms.mapper

import com.suluhulabs.hrms.dto.JobPostDto
import com.suluhulabs.hrms.model.JobPost

fun JobPost.toJobPostDto(): JobPostDto = JobPostDto(
    id = this.id,
    title = this.title,
    description = this.description,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
)