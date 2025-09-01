package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.ResponseBody
import com.suluhulabs.hrms.dto.UpdateProfileRequestDto
import com.suluhulabs.hrms.dto.UserDto
import com.suluhulabs.hrms.mapper.toUserDto
import com.suluhulabs.hrms.model.User
import com.suluhulabs.hrms.service.UserService
import com.suluhulabs.hrms.util.getUserPrincipalFromSecurityContext
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @GetMapping("/me")
    fun getAuthenticatedUser(): ResponseEntity<ResponseBody<UserDto>?> {
        val userPrincipal = getUserPrincipalFromSecurityContext()

        val targetUser: User = userService.getUserById(userPrincipal.getId())

        return ResponseEntity.ok()
            .body(ResponseBody(success = true, message = "User found successfully", data = targetUser.toUserDto()))

    }

    @PutMapping("/me/profile")
    fun updateAuthenticatedUserProfile(@Valid @RequestBody updateProfileRequestDto: UpdateProfileRequestDto): ResponseEntity<ResponseBody<UserDto>> {
        val principal = getUserPrincipalFromSecurityContext()

        val updatedUser = userService.updateUserProfile(userId = principal.getId(), updateProfileRequestDto)

        return ResponseEntity.ok().body(
            ResponseBody(
                success = true, message = "User profile updated successfully", data = updatedUser.toUserDto()
            )
        )
    }
}