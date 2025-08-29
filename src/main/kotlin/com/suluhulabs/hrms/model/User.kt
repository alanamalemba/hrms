package com.suluhulabs.hrms.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(name = "users")
data class User(

    @Column(name = "first_name")
    var firstName: String,

    @Column(name = "last_name")
    var lastName: String,

    @Column(name = "other_names")
    var otherNames: String?,

    @Column(unique = true)
    var email: String,

    @Column(name = "is_verified")
    var isVerified: Boolean? = false,

    @Column(name = "phone_number")
    var phoneNumber: String?,

    @Column(name = "hashed_password")
    var hashedPassword: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val organizationMemberships: MutableList<OrganizationMember> = mutableListOf()

) : BaseEntity()
