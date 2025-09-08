package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.dto.UpdateProfileRequestDto
import com.suluhulabs.hrms.model.Profile
import com.suluhulabs.hrms.model.User
import com.suluhulabs.hrms.repository.ProfileRepository
import com.suluhulabs.hrms.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class ProfileService(val userRepository: UserRepository, val profileRepository: ProfileRepository) {

    fun updateUserProfile(userId: Long, updateProfileRequestDto: UpdateProfileRequestDto): User {

        val targetUser = userRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User not found") }

        val userProfile: Profile = profileRepository.save(
            Profile(
                profession = updateProfileRequestDto.profession,
                birthDate = updateProfileRequestDto.birthDate,
                user = targetUser
            )
        )

        return targetUser.copy(profile = userProfile)
    }
}