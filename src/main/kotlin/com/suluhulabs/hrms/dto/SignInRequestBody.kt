package com.suluhulabs.hrms.dto

import jakarta.persistence.Column
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class SignInRequestBody(
    @field:Email
    val email: String,

    @field:NotBlank
    val password: String,
)
