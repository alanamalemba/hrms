package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.CreateOrganizationRequestDto
import com.suluhulabs.hrms.dto.OrganizationDto
import com.suluhulabs.hrms.dto.ResponseBodyDto
import com.suluhulabs.hrms.mapper.toDto
import com.suluhulabs.hrms.service.OrganizationService
import com.suluhulabs.hrms.util.getUserPrincipalFromSecurityContext
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class OrganizationController(val organizationService: OrganizationService) {

    @PostMapping("/organizations")
    fun createOrganization(@Valid @RequestBody createOrganizationRequestDto: CreateOrganizationRequestDto): ResponseEntity<ResponseBodyDto<OrganizationDto>?> {
        val principal = getUserPrincipalFromSecurityContext()
        val newOrganization = organizationService.createOrganization(createOrganizationRequestDto, principal.getId())

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseBodyDto(
                success = true,
                message = "Organization created successfully",
                data = newOrganization.toDto()
            )
        )
    }

    @GetMapping("/organizations/{organizationId}")
    fun getOrganizationById(@PathVariable organizationId: Long): ResponseEntity<ResponseBodyDto<OrganizationDto>?> {
        val organization = organizationService.getOrganizationById(organizationId)

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ResponseBodyDto(
                success = true,
                message = "Organization found successfully",
                data = organization.toDto()
            )
        )
    }
}