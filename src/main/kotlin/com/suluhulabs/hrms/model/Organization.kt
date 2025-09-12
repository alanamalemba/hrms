package com.suluhulabs.hrms.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity(name = "organizations")
data class Organization(
    val name: String,

    @OneToMany(mappedBy = "organization", cascade = [CascadeType.ALL], orphanRemoval = true)
    val members: MutableList<OrganizationMember> = mutableListOf(),

    @OneToMany(mappedBy = "organization", cascade = [CascadeType.ALL], orphanRemoval = true)
    val jopPosts: MutableList<JobPost> = mutableListOf()

) : BaseEntity()
