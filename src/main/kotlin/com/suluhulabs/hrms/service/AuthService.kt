package com.suluhulabs.hrms.service

import com.suluhulabs.hrms.dto.SignInRequestBody
import com.suluhulabs.hrms.dto.SignUpRequestBody
import com.suluhulabs.hrms.dto.VerifyEmailRequestBody
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

        sendVerificationEmail(signUpRequestBody.email, htmlContent)

        return "User of email ${signUpRequestBody.email} created successfully. Follow the verification link sent to the provided email to verify your identity"


    }

    fun verifyEmail(verifyEmailRequestBody: VerifyEmailRequestBody): String {
        val isTokenValid =
            jwtService.checkIsTokenValid(verifyEmailRequestBody.verificationToken, JwtService.TokenType.VERIFICATION)

        if (!isTokenValid) throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid auth token")

        val userId =
            jwtService.extractUserId(verifyEmailRequestBody.verificationToken, JwtService.TokenType.VERIFICATION)

        val targetUser = userRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist") }

        targetUser.isVerified = true

        userRepository.save(targetUser)

        return "User of email ${targetUser.email} verified successfully"

    }

    fun signIn(signInRequestBody: SignInRequestBody): Triple<String, String, User> {
        val targetUser = userRepository.findByEmail(signInRequestBody.email) ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "User does not exist"
        )


        val isPasswordValid = passwordEncoder.matches(signInRequestBody.password, targetUser.hashedPassword)

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

            sendVerificationEmail(signInRequestBody.email, htmlContent)


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
