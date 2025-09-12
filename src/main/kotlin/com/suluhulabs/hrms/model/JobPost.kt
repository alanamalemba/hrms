package com.suluhulabs.hrms.model

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.context.annotation.Description

@Entity(name = "job_postings")
data class JobPosting(
    val title: String,
    val description: String,

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    val organization: Organization
) : BaseEntity()
