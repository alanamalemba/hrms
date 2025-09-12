package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.JobApplicationDto
import com.suluhulabs.hrms.dto.PaginatedResponseBodyDto
import com.suluhulabs.hrms.dto.ResponseBodyDto
import com.suluhulabs.hrms.mapper.toDto
import com.suluhulabs.hrms.mapper.toPaginatedResponseBodyDto
import com.suluhulabs.hrms.service.JobApplicationService
import com.suluhulabs.hrms.util.getUserPrincipalFromSecurityContext
import jakarta.validation.constraints.NotBlank
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class JobApplicationController(val jobApplicationService: JobApplicationService) {

    @PostMapping("job-posts/{jobPostId}/job-applications")
    fun addJobApplication(@NotBlank @PathVariable jobPostId: Long): ResponseEntity<ResponseBodyDto<JobApplicationDto>?> {

        val userId = getUserPrincipalFromSecurityContext().getId()

        val jobApplication = jobApplicationService.addJobApplication(userId, jobPostId)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseBodyDto(
                success = true,
                message = "Application added successfully!",
                data = jobApplication.toDto()
            )
        )


    }

    @GetMapping("job-posts/{jobPostId}/job-applications")
    fun getJobApplicationsByJobPostId(
        @PathVariable jobPostId: Long,
        @ParameterObject pageable: Pageable
    ): PaginatedResponseBodyDto<List<JobApplicationDto>> {

        val jobApplicationsPage = jobApplicationService.getJobApplicationsByJobPostId(jobPostId, pageable)

        return jobApplicationsPage.toPaginatedResponseBodyDto(
            success = true,
            message = "Jop posts found successfully"
        ) {
            it.toDto()
        }

    }

    @GetMapping("/job-applications/{jobApplicationId}")
    fun getJobApplicationById(
        @PathVariable jobApplicationId: Long,
    ): ResponseEntity<ResponseBodyDto<JobApplicationDto>?> {

        val jobApplication = jobApplicationService.getJobApplicationById(jobApplicationId)

        return ResponseEntity.ok().body(
            ResponseBodyDto(
                success = true,
                message = "Job post found successfully",
                data = jobApplication.toDto()
            )
        )


    }


}

