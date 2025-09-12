package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.CreateJobPostRequestDto
import com.suluhulabs.hrms.dto.JobPostDto
import com.suluhulabs.hrms.dto.PaginatedResponseBodyDto
import com.suluhulabs.hrms.dto.ResponseBodyDto
import com.suluhulabs.hrms.mapper.toJobPostDto
import com.suluhulabs.hrms.mapper.toPaginatedResponseBodyDto
import com.suluhulabs.hrms.service.JobPostService
import com.suluhulabs.hrms.util.getUserPrincipalFromSecurityContext
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class JobPostController(
    val jobPostService: JobPostService
) {

    @PostMapping("/organizations/{organizationId}/job-posts")
    fun createOrganizationJobPositing(
        @PathVariable organizationId: Long,
        @Valid @RequestBody createJobPostRequestDto: CreateJobPostRequestDto
    ): ResponseEntity<ResponseBodyDto<JobPostDto>?> {

        val principal = getUserPrincipalFromSecurityContext()

        val newJobPost = jobPostService.createJobPost(
            actingUserId = principal.getId(),
            organizationId = organizationId,
            createJobPostRequestDto = createJobPostRequestDto
        )

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseBodyDto(
                success = true,
                data = newJobPost.toJobPostDto(),
                message = "Job posting created successfully"
            )
        )
    }

    @GetMapping("/job-posts")
    fun getJobPosts(
        @RequestParam query: String?,
        @RequestParam organizationId: Long?,
        @ParameterObject pageable: Pageable
    ): PaginatedResponseBodyDto<List<JobPostDto>> {

        val jobPostsPage = jobPostService.getJobPosts(query, organizationId, pageable)

        return jobPostsPage.toPaginatedResponseBodyDto(
            query = query ?: "",
            message = "Job postings fetched successfully"
        ) { it.toJobPostDto() }
    }

    @GetMapping("/job-posts/{jobPostId}")
    fun getJobPostById(@PathVariable jobPostId: Long): ResponseEntity<ResponseBodyDto<JobPostDto>?> {
        val jobPost = jobPostService.getJobPostById(jobPostId)

        return ResponseEntity.ok().body(
            ResponseBodyDto(
                success = true,
                message = "Job post found successfully",
                data = jobPost.toJobPostDto()
            )
        )
    }


}