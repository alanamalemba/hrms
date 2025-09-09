package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.JobApplicationDto
import com.suluhulabs.hrms.dto.ResponseBodyDto
import com.suluhulabs.hrms.mapper.toDto
import com.suluhulabs.hrms.service.JobApplicationService
import com.suluhulabs.hrms.util.getUserPrincipalFromSecurityContext
import jakarta.validation.constraints.NotBlank
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JobApplicationController(val jobApplicationService: JobApplicationService) {

    @PostMapping("job-postings/{jobPostingId}/job-applications")
    fun addJobApplication(@NotBlank @PathVariable jobPostingId: Long): ResponseEntity<ResponseBodyDto<JobApplicationDto>?> {

        val userId = getUserPrincipalFromSecurityContext().getId()

        val jobApplication = jobApplicationService.addJobApplication(userId, jobPostingId)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseBodyDto(
                success = true,
                message = "Application added successfully!",
                data = jobApplication.toDto()
            )
        )


    }

//    @GetMapping("job-postings/{jobPostingId}/job-applications")
//    fun getJobApplications() {
//
//    }


}

