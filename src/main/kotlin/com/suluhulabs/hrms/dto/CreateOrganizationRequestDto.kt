package com.suluhulabs.hrms.dto

import jakarta.validation.constraints.NotBlank

data class CreateOrganizationRequestDto (
    @field:NotBlank var name:String
)
