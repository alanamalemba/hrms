package com.suluhulabs.hrms.controller

import com.suluhulabs.hrms.dto.ResponseBodyDto
import com.suluhulabs.hrms.dto.UserDto
import com.suluhulabs.hrms.mapper.toUserDto
import com.suluhulabs.hrms.model.User
import com.suluhulabs.hrms.service.UserService
import com.suluhulabs.hrms.util.getUserPrincipalFromSecurityContext
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(val userService: UserService) {

    @GetMapping("/me")
    fun getAuthenticatedUser(): ResponseEntity<ResponseBodyDto<UserDto>?> {
        val userPrincipal = getUserPrincipalFromSecurityContext()

        val targetUser: User = userService.getUserById(userPrincipal.getId())

        return ResponseEntity.ok()
            .body(ResponseBodyDto(success = true, message = "User found successfully", data = targetUser.toUserDto()))

    }
}