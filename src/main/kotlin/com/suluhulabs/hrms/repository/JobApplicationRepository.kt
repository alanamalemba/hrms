package com.suluhulabs.hrms.repository

import com.suluhulabs.hrms.model.JobApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JobApplicationRepository: JpaRepository<JobApplication, Long> {
}