package com.suluhulabs.hrms.model

import jakarta.persistence.*

@Entity(name = "job_applications")
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "job_posting_id"])])
data class JobApplication(
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "job_posting_id", nullable = false)
    val jobPosting: JobPosting,

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "ENUM('APPLIED', 'UNDER_REVIEW', 'INTERVIEW', 'OFFERED', 'HIRED', 'REJECTED', 'WITHDRAWN') DEFAULT 'APPLIED'"
    )
    var status: Status = Status.APPLIED

) : BaseEntity() {
    enum class Status {
        APPLIED,        // Candidate submitted application
        UNDER_REVIEW,   // HR/Recruiter screening
        INTERVIEW,      // One or more interviews scheduled/completed
        OFFERED,        // Company extended an offer
        HIRED,          // Candidate accepted and joined
        REJECTED,       // Application was rejected
        WITHDRAWN       // Candidate withdrew
    }
}