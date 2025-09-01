package com.suluhulabs.hrms.dto

import com.suluhulabs.hrms.model.Profile
import java.time.LocalDateTime

data class UserDto(
    val id: Long? = null,
    var firstName: String,
    var lastName: String,
    var otherNames: String?,
    var email: String,
    var isVerified: Boolean? = false,
    var phoneNumber: String?,
    val profile: ProfileDto?,
    val createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
)
