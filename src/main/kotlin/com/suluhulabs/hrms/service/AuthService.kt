package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.dto.SignUpRequestBody
import com.suluhulabs.hrms.exception.ConflictException
import com.suluhulabs.hrms.model.User
import com.suluhulabs.hrms.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(val userRepository: UserRepository, val passwordEncoder: PasswordEncoder) {

    fun singUp(signUpRequestBody: SignUpRequestBody): String {

        val existingUser = userRepository.findByEmail(signUpRequestBody.email)

        if (existingUser != null) throw ConflictException("User of email ${signUpRequestBody.email} already exists")

        val hashedPassword = passwordEncoder.encode(signUpRequestBody.password)

        val newUser = userRepository.save(
            User(
                email = signUpRequestBody.email,
                firstName = signUpRequestBody.firstName,
                lastName = signUpRequestBody.lastName,
                otherNames = signUpRequestBody.otherNames,
                phoneNumber = signUpRequestBody.phoneNumber,
                hashedPassword = hashedPassword,
            )
        )

        // todo: send email to user's provided email to verify email

        return "User of email ${signUpRequestBody.email} created successfully. Follow the verification link sent to the provided email to verify your identity"


    }

}
