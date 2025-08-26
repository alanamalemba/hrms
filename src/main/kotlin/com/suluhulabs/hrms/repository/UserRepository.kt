package com.suluhulabs.hrms.repository

import com.suluhulabs.hrms.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}