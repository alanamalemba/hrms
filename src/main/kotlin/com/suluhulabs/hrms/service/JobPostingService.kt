package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.dto.CreateJobPostingRequestDto
import com.suluhulabs.hrms.model.JobPosting
import com.suluhulabs.hrms.model.OrganizationMember
import com.suluhulabs.hrms.repository.JobPostingRepository
import com.suluhulabs.hrms.repository.OrganizationMemberRepository
import com.suluhulabs.hrms.repository.OrganizationRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class JobPostingService(
    val organizationMemberRepository: OrganizationMemberRepository,
    val jobPostingRepository: JobPostingRepository,
    val organizationRepository: OrganizationRepository
) {

    fun createJobPosting(
        actingUserId: Long,
        createJobPostingRequestDto: CreateJobPostingRequestDto,
        organizationId: Long
    ): JobPosting {

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


    return jobPostingRepository.save(
            JobPosting(
                title = createJobPostingRequestDto.title,
                description = createJobPostingRequestDto.description,
                organization = targetOrganization,
            )
        )


    }

}
