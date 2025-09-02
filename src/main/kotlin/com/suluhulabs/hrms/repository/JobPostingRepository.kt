package com.suluhulabs.hrms.repository

import com.suluhulabs.hrms.model.JobPosting
import org.springframework.data.jpa.repository.JpaRepository

interface JobPostingRepository : JpaRepository<JobPosting, Long>