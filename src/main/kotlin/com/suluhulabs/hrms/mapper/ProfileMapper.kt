package com.suluhulabs.hrms.mapper

import com.suluhulabs.hrms.dto.ProfileDto
import com.suluhulabs.hrms.model.Profile

fun Profile.toProfileDto(): ProfileDto = ProfileDto(
    id = this.id,
    profession = this.profession,
    birthDate = this.birthDate,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt

)