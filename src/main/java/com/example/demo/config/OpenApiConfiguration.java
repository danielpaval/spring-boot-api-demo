package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI applicationOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Learning Management System (LMS) API")
                        .version("v1"));
    }
}

