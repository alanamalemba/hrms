package com.suluhulabs.hrms.repository

import com.suluhulabs.hrms.model.JobApplication
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobApplicationRepository : JpaRepository<JobApplication, Long> {

    fun findByJobPostId(jobPostId: Long, pageable: Pageable): Page<JobApplication>
}