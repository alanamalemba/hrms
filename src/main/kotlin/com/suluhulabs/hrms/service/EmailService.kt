package com.suluhulabs.hrms.service

import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.MailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(
    private val mailSender: JavaMailSender,
    @param:Value("\${spring.mail.username}") private val fromEmailAddress: String
) {
    fun sendSimpleMail(to: String, subject: String, body: String) {
        val message = SimpleMailMessage().apply {
            setTo(to)
            from = fromEmailAddress
            setSubject(subject)
            text = body
        }

        mailSender.send(message)
    }

    fun sendHtmlEmail(to: String, subject: String, htmlContent: String) {
        val message: MimeMessage = mailSender.createMimeMessage()
        MimeMessageHelper(message, false, "UTF-8").apply {
            setTo(to)
            setFrom(fromEmailAddress)
            setSubject(subject)
            setText(htmlContent, true)
        }

        mailSender.send(message)
    }
}