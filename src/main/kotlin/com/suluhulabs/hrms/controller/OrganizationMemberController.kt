package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.AddMemberToOrganizationRequestDto
import com.suluhulabs.hrms.dto.ResponseBodyDto
import com.suluhulabs.hrms.dto.UpdateOrganizationMemberRequestDto
import com.suluhulabs.hrms.service.OrganizationMemberService
import com.suluhulabs.hrms.util.getUserPrincipalFromSecurityContext
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/organizations/{organizationId}/members")
class OrganizationMemberController(val organizationMemberService: OrganizationMemberService) {

    @PostMapping
    fun addMemberToOrganization(
        @PathVariable organizationId: Long,
        @Valid @RequestBody addMemberToOrganizationRequestDto: AddMemberToOrganizationRequestDto
    ): ResponseEntity<ResponseBodyDto<Unit>> {
        organizationMemberService.addUserToOrganization(
            organizationId = organizationId,
            userId = addMemberToOrganizationRequestDto.userId,
            role = addMemberToOrganizationRequestDto.role
        )


        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseBodyDto(success = true, message = "User added to organization successfully"))
    }

    @PatchMapping("/{userId}")
    fun updateOrganizationMember(
        @PathVariable organizationId: Long,
        @PathVariable userId: Long,
        @Valid @RequestBody updateOrganizationMemberRequestDto: UpdateOrganizationMemberRequestDto
    ): ResponseEntity<ResponseBodyDto<Unit>> {

        val principal = getUserPrincipalFromSecurityContext()

        organizationMemberService.updateOrganizationUser(organizationId, userId, updateOrganizationMemberRequestDto, actingUserId= principal.getId())

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseBodyDto(success = true, message = "Organization member updated successfully"))
    }


}