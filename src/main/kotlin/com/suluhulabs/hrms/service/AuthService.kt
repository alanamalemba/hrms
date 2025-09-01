package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.dto.SignInRequestDto
import com.suluhulabs.hrms.dto.SignUpRequestDto
import com.suluhulabs.hrms.dto.VerifyEmailRequestDto
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
    @param:Value("\${app.client.url}") val clientUrl: String
) {

    private val emailVerificationUrl = "$clientUrl/verify-email"


    @Transactional
    fun signUp(signUpRequestDto: SignUpRequestDto): String {

        //check if user exists
        val existingUser = userRepository.findByEmail(signUpRequestDto.email)

        // throw error if user exists
        if (existingUser != null) throw ResponseStatusException(
            HttpStatus.CONFLICT,
            "User of email ${signUpRequestDto.email} already exists"
        )

        // hash the password and create the new user
        val hashedPassword = passwordEncoder.encode(signUpRequestDto.password)

        val newUser = userRepository.save(
            User(
                email = signUpRequestDto.email,
                firstName = signUpRequestDto.firstName,
                lastName = signUpRequestDto.lastName,
                otherNames = signUpRequestDto.otherNames,
                phoneNumber = signUpRequestDto.phoneNumber,
                hashedPassword = hashedPassword,
            )
        )

        // send email for user to verify that they own the email
        val verificationToken = jwtService.generateToken(newUser.id!!, JwtService.TokenType.VERIFICATION)

        val htmlContent = """
                <html>
                  <body>
                    <p>Hi ${signUpRequestDto.firstName},</p>
                    <p>Thank you for signing up! Please verify your email address by clicking the link below:</p>
                    <p><a href="$emailVerificationUrl?verificationToken=$verificationToken">Verify My Email</a></p>
                    <p>If you did not create an account, you can ignore this email.</p>
                    <p>Best regards,<br/>The HRMS Team</p>
                  </body>
                </html>
            """.trimIndent()

        sendVerificationEmail(signUpRequestDto.email, htmlContent)

        return "User of email ${signUpRequestDto.email} created successfully. Follow the verification link sent to the provided email to verify your identity"


    }

    fun verifyEmail(verifyEmailRequestDto: VerifyEmailRequestDto): String {
        val isTokenValid =
            jwtService.checkIsTokenValid(verifyEmailRequestDto.verificationToken, JwtService.TokenType.VERIFICATION)

        if (!isTokenValid) throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid auth token")

        val userId =
            jwtService.extractUserId(verifyEmailRequestDto.verificationToken, JwtService.TokenType.VERIFICATION)

        val targetUser = userRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist") }

        targetUser.isVerified = true

        userRepository.save(targetUser)

        return "User of email ${targetUser.email} verified successfully"

    }

    fun signIn(signInRequestDto: SignInRequestDto): Triple<String, String, User> {
        val targetUser = userRepository.findByEmail(signInRequestDto.email) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "User does not exist"
        )


        val isPasswordValid = passwordEncoder.matches(signInRequestDto.password, targetUser.hashedPassword)

        if (!isPasswordValid) throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials")

        // check if user is verified
        if (!targetUser.isVerified!!) {
            val verificationToken = jwtService.generateToken(targetUser.id!!, JwtService.TokenType.VERIFICATION)

            val htmlContent = """
                <html>
                  <body>
                    <p>Hi ${targetUser.firstName},</p>
                    <p>Before we sign you in, please verify your email address by clicking the link below:</p>
                    <p><a href="$emailVerificationUrl?verificationToken=$verificationToken">Verify My Email</a></p>
                    <p>If you did not create an account, you can ignore this email.</p>
                    <p>Best regards,<br/>The HRMS Team</p>
                  </body>
                </html>
            """.trimIndent()

            sendVerificationEmail(signInRequestDto.email, htmlContent)


            throw ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Email account is not verified. Please verify the email by clicking the verification link sent to the provided email"
            )
        }

        val accessToken = jwtService.generateToken(targetUser.id!!, JwtService.TokenType.ACCESS)
        val refreshToken = jwtService.generateToken(targetUser.id!!, JwtService.TokenType.REFRESH)

        return Triple(accessToken, refreshToken, targetUser)
    }

    private fun sendVerificationEmail(recipientEmail: String, htmlContent: String) {
        try {
            emailService.sendHtmlEmail(
                to = recipientEmail,
                subject = "HRMS User Email Verification",
                htmlContent = htmlContent,
            )
        } catch (ex: Exception) {
            throw RuntimeException("Failed to send verification email", ex)
        }
    }

}
