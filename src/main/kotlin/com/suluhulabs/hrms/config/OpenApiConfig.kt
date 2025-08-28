package com.suluhulabs.hrms.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun customOpenApi(): OpenAPI = OpenAPI().info(
        Info().title("HRMS API documentation").version("1.0").description("API documentation for HRMS api server")
    )
}