package com.suluhulabs.hrms.repository

import com.suluhulabs.hrms.model.OrganizationMember
import org.springframework.data.jpa.repository.JpaRepository

interface OrganizationMemberRepository : JpaRepository<OrganizationMember, Long> {
    fun findByOrganizationIdAndUserId(organizationId: Long, userId: Long): OrganizationMember?
}
