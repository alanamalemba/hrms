package com.suluhulabs.hrms.dto

data class SignInResponseDto(
    val user: UserDto,
    val accessToken: String
)
