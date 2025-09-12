package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.dto.CreateJobPostRequestDto
import com.suluhulabs.hrms.model.JobPost
import com.suluhulabs.hrms.model.OrganizationMember
import com.suluhulabs.hrms.repository.JobPostRepository
import com.suluhulabs.hrms.repository.OrganizationMemberRepository
import com.suluhulabs.hrms.repository.OrganizationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class JobPostService(
    val organizationMemberRepository: OrganizationMemberRepository,
    val jobPostRepository: JobPostRepository,
    val organizationRepository: OrganizationRepository
) {

    fun createJobPost(
        actingUserId: Long,
        createJobPostRequestDto: CreateJobPostRequestDto,
        organizationId: Long
    ): JobPost {

        val actingMember = organizationMemberRepository.findByOrganizationIdAndUserId(
            organizationId = organizationId,
            userId = actingUserId
        )
            ?: throw ResponseStatusException(
                HttpStatus.NOT_FOUND, "You are not a member of this organization"
            )

        if (actingMember.role != OrganizationMember.Role.ADMIN) throw ResponseStatusException(
            HttpStatus.FORBIDDEN,
            "You do not have the necessary rights to update a user of this organization "
        )

        val targetOrganization = organizationRepository.findById(organizationId).orElseThrow {
            ResponseStatusException(
                HttpStatus.NOT_FOUND, "Organization not found"
            )
        }


        return jobPostRepository.save(
            JobPost(
                title = createJobPostRequestDto.title,
                description = createJobPostRequestDto.description,
                organization = targetOrganization,
            )
        )


    }

    fun getJobPosts(
        query: String?,
        organizationId: Long?,
        pageable: Pageable
    ): Page<JobPost> {

        return jobPostRepository.findJobPosts(query ,organizationId, pageable)
    }

}
