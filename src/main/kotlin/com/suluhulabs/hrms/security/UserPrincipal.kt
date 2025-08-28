package com.suluhulabs.hrms.security

import com.suluhulabs.hrms.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal(val user: User) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        return emptyList()
    }

    override fun getPassword(): String {
        return user.hashedPassword
    }

    override fun getUsername(): String {
        return user.email
    }

    fun getId(): Long = user.id!!
}