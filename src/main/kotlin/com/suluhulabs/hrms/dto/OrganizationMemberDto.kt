package com.suluhulabs.hrms.dto

import com.suluhulabs.hrms.model.OrganizationMember
import java.time.LocalDateTime

class OrganizationMemberDto(
    val id: Long? = null,
    val user: UserDto,
    val organization: OrganizationDto,
    val role: OrganizationMember.Role,
    val status: OrganizationMember.Status,
    val createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
)