package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.CreateJobPostingRequestDto
import com.suluhulabs.hrms.dto.JobPostingDto
import com.suluhulabs.hrms.dto.ResponseBodyDto
import com.suluhulabs.hrms.mapper.toJobPostingDto
import com.suluhulabs.hrms.model.JobPosting
import com.suluhulabs.hrms.service.JobPostingService
import com.suluhulabs.hrms.util.getUserPrincipalFromSecurityContext
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class JobPostingController(
    val jobPostingService: JobPostingService
) {

    @PostMapping("/organizations/{organizationId}/job-postings")
    fun createOrganizationJobPositing(
        @PathVariable organizationId: Long,
        @Valid @RequestBody createJobPostingRequestDto: CreateJobPostingRequestDto
    ): ResponseEntity<ResponseBodyDto<JobPostingDto>?> {

        val principal = getUserPrincipalFromSecurityContext()

        val newJobPosting = jobPostingService.createJobPosting(
            actingUserId = principal.getId(),
            organizationId = organizationId,
            createJobPostingRequestDto = createJobPostingRequestDto
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseBodyDto(
                success = true,
                data = newJobPosting.toJobPostingDto(),
                message = "Job posting created successfully"
            )
        )
    }

}