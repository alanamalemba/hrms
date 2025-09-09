package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.exception.NotFoundException
import com.suluhulabs.hrms.model.JobApplication
import com.suluhulabs.hrms.repository.JobApplicationRepository
import com.suluhulabs.hrms.repository.JobPostingRepository
import com.suluhulabs.hrms.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class JobApplicationService(
    val jobApplicationRepository: JobApplicationRepository,
    val userRepository: UserRepository,
    val jobPostingRepository: JobPostingRepository
) {
    fun addJobApplication(userId: Long, jobPostingId: Long): JobApplication {

        val user = userRepository.findById(userId).orElseThrow { NotFoundException("User not found!") }

        val jobPosting =
            jobPostingRepository.findById(jobPostingId).orElseThrow { NotFoundException("Job Posting not found!") }

        return jobApplicationRepository.save(JobApplication(user = user, jobPosting = jobPosting))
    }
}