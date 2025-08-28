package com.suluhulabs.hrms.dto

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

data class UserDto(
    val id: Long? = null,

    var firstName: String,

    var lastName: String,

    var otherNames: String?,

    var email: String,

    var isVerified: Boolean? = false,

    var phoneNumber: String?,

    val createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null,
)
