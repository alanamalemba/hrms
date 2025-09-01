package com.suluhulabs.hrms.repository

import com.suluhulabs.hrms.model.Organization
import org.springframework.data.jpa.repository.JpaRepository

interface OrganizationRepository: JpaRepository<Organization, Long>