package com.suluhulabs.hrms.dto

import com.suluhulabs.hrms.model.OrganizationMember
import jakarta.validation.constraints.NotBlank

data class AddMemberToOrganizationRequestDto(
    @field:NotBlank
    val userId: Long,

    @field:NotBlank
    val role: OrganizationMember.Role
)
