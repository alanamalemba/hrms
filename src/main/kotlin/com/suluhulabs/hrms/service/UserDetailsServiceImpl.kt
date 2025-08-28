package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.repository.UserRepository
import com.suluhulabs.hrms.security.UserPrincipal
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserDetailsServiceImpl(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails? {
        val targetUser =
            userRepository.findByEmail(email) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

        return UserPrincipal(targetUser)
    }

    fun loadUserById(userId: Long): UserDetails? {
        val targetUser = userRepository.findById(userId)
            .orElseThrow { throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }

        return UserPrincipal(targetUser)
    }
}