package com.suluhulabs.hrms.dto

import jakarta.validation.constraints.NotBlank
import jdk.jfr.Description

data class CreateJobPostingRequestDto(
    @field:NotBlank val title: String,
    @field:NotBlank val description: String
)