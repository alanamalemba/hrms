package com.suluhulabs.hrms.dto

import com.suluhulabs.hrms.model.OrganizationMember

data class UpdateOrganizationMemberRequestDto(
    val role: OrganizationMember.Role?,
    val status: OrganizationMember.Status?
)
