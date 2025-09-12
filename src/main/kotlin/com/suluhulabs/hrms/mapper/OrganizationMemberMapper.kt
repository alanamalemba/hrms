package com.suluhulabs.hrms.mapper

import com.suluhulabs.hrms.dto.OrganizationMemberDto
import com.suluhulabs.hrms.model.OrganizationMember

fun OrganizationMember.toDto(): OrganizationMemberDto = OrganizationMemberDto(
    id = this.id,
    user = this.user.toUserDto(),
    organization = this.organization.toDto(),
    status = this.status,
    role = this.role,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)