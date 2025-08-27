package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.dto.SignUpRequestBody
import com.suluhulabs.hrms.model.User
import com.suluhulabs.hrms.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService(
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val emailService: EmailService,
    val jwtService: JwtService,
    @param:Value("\${app.server.url}") val serverUrl: String
) {

    private val emailVerificationUrl = "$serverUrl/auth/verify-email"


    @Transactional
    fun signUp(signUpRequestBody: SignUpRequestBody): String {

        //check if user exists
        val existingUser = userRepository.findByEmail(signUpRequestBody.email)

        // throw error if user exists
        if (existingUser != null) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "User of email ${signUpRequestBody.email} already exists"
        )

        // hash the password and create the new user
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

        // send email for user to verify that they own the email
        val verificationToken = jwtService.generateToken(newUser.id!!, JwtService.TokenType.VERIFICATION)

        val htmlContent = """
                <html>
                  <body>
                    <p>Hi ${signUpRequestBody.firstName},</p>
                    <p>Thank you for signing up! Please verify your email address by clicking the link below:</p>
                    <p><a href="$emailVerificationUrl?verificationToken=$verificationToken">Verify My Email</a></p>
                    <p>If you did not create an account, you can ignore this email.</p>
                    <p>Best regards,<br/>The HRMS Team</p>
                  </body>
                </html>
            """.trimIndent()

        try {
            emailService.sendHtmlEmail(
                to = signUpRequestBody.email,
                subject = "HRMS User Email Verification",
                htmlContent = htmlContent,
            )
        } catch (ex: Exception) {
            throw RuntimeException("Failed to send verification email", ex)
        }

        return "User of email ${signUpRequestBody.email} created successfully. Follow the verification link sent to the provided email to verify your identity"


    }

}
