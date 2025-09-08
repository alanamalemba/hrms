package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.model.User
import com.suluhulabs.hrms.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(val userRepository: UserRepository) {
    fun getUserById(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found") }
    }


}