package com.raoni.crmplatform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Enterprise AI CRM Automation Platform")
                .version("0.1.0")
                .description("CRM automation platform with lead intake, qualification, integrations, audit, and event-driven boundaries."));
    }
}
