package com.suluhulabs.hrms.dto

import com.suluhulabs.hrms.model.OrganizationMember

data class UpdateOrganizationUserRequestDto(
    val role: OrganizationMember.Role?,
    val status: OrganizationMember.Status?
)
