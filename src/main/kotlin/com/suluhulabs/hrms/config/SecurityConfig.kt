package com.suluhulabs.hrms.config

import com.suluhulabs.hrms.security.JwtAuthFilter
import com.suluhulabs.hrms.security.RestAccessDeniedHandler
import com.suluhulabs.hrms.security.RestAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.beans.BeanProperty

@Configuration
class SecurityConfig(
    val jwtAuthFilter: JwtAuthFilter,
    val restAuthenticationEntryPoint: RestAuthenticationEntryPoint,
    val restAccessDeniedHandler: RestAccessDeniedHandler
) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/auth/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .exceptionHandling { exceptions ->
                exceptions
                    .authenticationEntryPoint(restAuthenticationEntryPoint)  // 401
                    .accessDeniedHandler(restAccessDeniedHandler)  // 403
            }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}