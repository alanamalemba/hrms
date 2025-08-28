package com.suluhulabs.hrms.util

import com.suluhulabs.hrms.security.UserPrincipal
import org.springframework.security.core.context.SecurityContextHolder

fun getUserPrincipalFromSecurityContext(): UserPrincipal {
    return (SecurityContextHolder.getContext().authentication.principal as UserPrincipal)
}