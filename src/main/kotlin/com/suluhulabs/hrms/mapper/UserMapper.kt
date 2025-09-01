package com.suluhulabs.hrms.mapper

import com.suluhulabs.hrms.dto.UserDto
import com.suluhulabs.hrms.model.User


fun User.toUserDto(): UserDto = UserDto(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    otherNames = this.otherNames,
    email = this.email,
    isVerified = this.isVerified,
    phoneNumber = this.phoneNumber,
    profile = this.profile?.toProfileDto(),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)