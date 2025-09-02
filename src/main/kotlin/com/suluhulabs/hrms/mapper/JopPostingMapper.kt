package com.suluhulabs.hrms.mapper

import com.suluhulabs.hrms.dto.JobPostingDto
import com.suluhulabs.hrms.model.JobPosting

fun JobPosting.toJobPostingDto(): JobPostingDto = JobPostingDto(
    id = this.id,
    title = this.title,
    description = this.description,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
)