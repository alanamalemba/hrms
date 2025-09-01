package com.suluhulabs.hrms.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDate

@Entity(name = "profiles")
data class Profile(
    val profession: String?,

    @Column(name = "birth_date")
    val birthDate: LocalDate?,

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User
) : BaseEntity()
