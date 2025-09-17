package com.suluhulabs.hrms.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    val securitySchemeName = "bearerAuth"

    @Bean
    fun customOpenApi(): OpenAPI = OpenAPI().info(
        Info().title("HRMS API documentation").version("1.0").description("API documentation for HRMS api server")
    ).addSecurityItem(SecurityRequirement().addList(securitySchemeName))
        .components(
            Components().addSecuritySchemes(
                securitySchemeName, SecurityScheme().name(securitySchemeName).type(
                    SecurityScheme.Type.HTTP
                ).scheme("bearer").bearerFormat("JWT")
            )
        )
}