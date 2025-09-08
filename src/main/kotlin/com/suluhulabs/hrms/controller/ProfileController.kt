package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.ResponseBodyDto
import com.suluhulabs.hrms.dto.UpdateProfileRequestDto
import com.suluhulabs.hrms.dto.UserDto
import com.suluhulabs.hrms.mapper.toUserDto
import com.suluhulabs.hrms.service.ProfileService
import com.suluhulabs.hrms.util.getUserPrincipalFromSecurityContext
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfileController(val profileService: ProfileService) {


    @PutMapping("users/me/profile")
    fun updateAuthenticatedUserProfile(@Valid @RequestBody updateProfileRequestDto: UpdateProfileRequestDto): ResponseEntity<ResponseBodyDto<UserDto>> {
        val principal = getUserPrincipalFromSecurityContext()

        val updatedUser = profileService.updateUserProfile(userId = principal.getId(), updateProfileRequestDto)

        return ResponseEntity.ok().body(
            ResponseBodyDto(
                success = true, message = "User profile updated successfully", data = updatedUser.toUserDto()
            )
        )
    }
}