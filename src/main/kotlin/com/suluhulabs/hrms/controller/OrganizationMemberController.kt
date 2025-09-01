package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.AddUserToOrganizationRequestDto
import com.suluhulabs.hrms.dto.ResponseBody
import com.suluhulabs.hrms.dto.UpdateOrganizationUserRequestDto
import com.suluhulabs.hrms.service.OrganizationMemberService
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
        @Valid @RequestBody addUserToOrganizationRequestDto: AddUserToOrganizationRequestDto
    ): ResponseEntity<ResponseBody<Unit>> {
        organizationMemberService.addUserToOrganization(
            organizationId = organizationId,
            userId = addUserToOrganizationRequestDto.userId,
            role = addUserToOrganizationRequestDto.role
        )


        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseBody(success = true, message = "User added to organization successfully"))
    }

    @PatchMapping("/{userId}")
    fun updateOrganizationMember(
        @PathVariable organizationId: Long,
        @PathVariable userId: Long,
        @Valid @RequestBody updateOrganizationUserRequestDto: UpdateOrganizationUserRequestDto
    ): ResponseEntity<ResponseBody<Unit>> {
        organizationMemberService.updateOrganizationUser(organizationId, userId, updateOrganizationUserRequestDto)

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ResponseBody(success = true, message = "Organization member updated successfully"))
    }


}