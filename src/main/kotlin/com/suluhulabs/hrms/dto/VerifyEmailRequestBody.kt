package com.suluhulabs.hrms.dto

import jakarta.validation.constraints.NotBlank

data class VerifyEmailRequestBody(@field:NotBlank val verificationToken: String)
