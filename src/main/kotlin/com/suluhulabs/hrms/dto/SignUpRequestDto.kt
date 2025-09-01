package com.suluhulabs.hrms.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class SignUpRequestDto(

    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    val otherNames: String?,

    @field:Email
    val email: String,

    val phoneNumber: String?,

    @field:Pattern(
        regexp = "^.{6,}$",
        message = "Password must be at least  6 characters"
    )
    val password: String,

    )
