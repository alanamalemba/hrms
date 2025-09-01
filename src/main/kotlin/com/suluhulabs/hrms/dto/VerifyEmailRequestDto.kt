package com.suluhulabs.hrms.dto

import jakarta.validation.constraints.NotBlank

data class VerifyEmailRequestDto(@field:NotBlank val verificationToken: String)
