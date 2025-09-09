package com.suluhulabs.hrms.mapper

import com.suluhulabs.hrms.dto.JobApplicationDto
import com.suluhulabs.hrms.model.JobApplication

fun JobApplication.toDto(): JobApplicationDto = JobApplicationDto(
    id = this.id,
    user = this.user.toUserDto(),
    jobPosting = this.jobPosting.toJobPostingDto(),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    status = this.status
)