package com.suluhulabs.hrms.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity(name = "organization_members")
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "organization_id"])])
data class OrganizationMember(
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    val organization: Organization,
) : BaseEntity()
