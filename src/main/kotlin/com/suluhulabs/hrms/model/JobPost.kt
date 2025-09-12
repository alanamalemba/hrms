package com.suluhulabs.hrms.model

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "job_posts")
data class JobPost(
    val title: String,
    val description: String,

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    val organization: Organization
) : BaseEntity()
