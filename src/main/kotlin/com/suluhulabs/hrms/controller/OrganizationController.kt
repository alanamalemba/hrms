package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.CreateOrganizationRequestDto
import com.suluhulabs.hrms.service.OrganizationService
import com.suluhulabs.hrms.util.getUserPrincipalFromSecurityContext
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/organizations")
class OrganizationController(val organizationService : OrganizationService) {

    @PostMapping
    fun createOrganization(@Valid @RequestBody createOrganizationRequestDto: CreateOrganizationRequestDto){
        val principal = getUserPrincipalFromSecurityContext()
        organizationService.createOrganization(createOrganizationRequestDto, principal.getId())


    }
}