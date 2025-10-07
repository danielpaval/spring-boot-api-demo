package com.example.demo.config;

import com.example.demo.security.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    @NonNull
    public Optional<Long> getCurrentAuditor() {
        try {
            Long userId = SecurityUtils.getUserId();
            return Optional.ofNullable(userId);
        } catch (Exception e) {
            // If there's no authenticated user or any security context issue,
            // return empty (e.g., during system operations or tests)
            return Optional.empty();
        }
    }

}
