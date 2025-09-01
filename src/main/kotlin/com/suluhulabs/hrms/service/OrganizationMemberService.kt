package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.dto.UpdateOrganizationUserRequestDto
import com.suluhulabs.hrms.model.OrganizationMember
import com.suluhulabs.hrms.repository.OrganizationRepository
import com.suluhulabs.hrms.repository.OrganizationMemberRepository
import com.suluhulabs.hrms.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class OrganizationMemberService(
    val organizationMemberRepository: OrganizationMemberRepository,
    val userRepository: UserRepository,
    val organizationRepository: OrganizationRepository
) {
    fun addUserToOrganization(userId: Long, organizationId: Long, role: OrganizationMember.Role) {

        val targetUser = userRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }

        val targetOrganization = organizationRepository.findById(organizationId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Organization not found") }

        organizationMemberRepository.save(
            OrganizationMember(
                user = targetUser,
                organization = targetOrganization,
                role = role
            )
        )

    }

    fun updateOrganizationUser(
        organizationId: Long,
        useId: Long,
        updateOrganizationUserRequestDto: UpdateOrganizationUserRequestDto
    ) {
        val organizationUser =
            organizationMemberRepository.findByOrganizationIdAndUserId(organizationId = organizationId, userId = useId)
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Organization User not found"
                )

        updateOrganizationUserRequestDto.role?.let { organizationUser.role = it }
        updateOrganizationUserRequestDto.status?.let { organizationUser.status = it }

        organizationMemberRepository.save(organizationUser)



    }


}
