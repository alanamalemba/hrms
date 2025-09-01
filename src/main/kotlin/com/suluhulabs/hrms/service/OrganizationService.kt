package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.dto.CreateOrganizationRequestDto
import com.suluhulabs.hrms.model.Organization
import com.suluhulabs.hrms.model.OrganizationMember
import com.suluhulabs.hrms.repository.OrganizationRepository
import com.suluhulabs.hrms.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class OrganizationService(
    val organizationRepository: OrganizationRepository,
    val organizationMemberService: OrganizationMemberService,
    val userRepository: UserRepository
) {
    @Transactional
    fun createOrganization(createOrganizationRequestDto: CreateOrganizationRequestDto, principalId: Long) {


        val newOrganization = organizationRepository.save(Organization(name = createOrganizationRequestDto.name))

        organizationMemberService.addUserToOrganization(principalId, newOrganization.id!!, OrganizationMember.Role.ADMIN)
    }
}
