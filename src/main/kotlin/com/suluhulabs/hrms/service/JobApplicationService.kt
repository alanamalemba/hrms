package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.exception.NotFoundException
import com.suluhulabs.hrms.model.JobApplication
import com.suluhulabs.hrms.repository.JobApplicationRepository
import com.suluhulabs.hrms.repository.JobPostRepository
import com.suluhulabs.hrms.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class JobApplicationService(
    val jobApplicationRepository: JobApplicationRepository,
    val userRepository: UserRepository,
    val jobPostRepository: JobPostRepository
) {
    fun addJobApplication(userId: Long, jobPostId: Long): JobApplication {

        val user = userRepository.findById(userId).orElseThrow { NotFoundException("User not found!") }

        val jobPost = jobPostRepository.findById(jobPostId).orElseThrow { NotFoundException("Job Posting not found!") }

        return jobApplicationRepository.save(JobApplication(user = user, jobPost = jobPost))
    }

    fun getJobApplicationsByJobPostId(
        jobPostId: Long, pageable: Pageable
    ): Page<JobApplication> {

        return jobApplicationRepository.findByJobPostId(jobPostId, pageable)

    }
}