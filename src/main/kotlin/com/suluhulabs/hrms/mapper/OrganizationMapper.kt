package com.suluhulabs.hrms.mapper

import com.suluhulabs.hrms.dto.OrganizationDto
import com.suluhulabs.hrms.model.Organization

fun Organization.toDto(): OrganizationDto = OrganizationDto(
    id = this.id,
    name = this.name,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)