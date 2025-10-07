package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;

@Configuration
@EnableEnversRepositories(basePackages = "com.example.demo.repository")
public class EnversAuditingConfiguration {
}
