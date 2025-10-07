package com.example.common.entity;

import com.example.demo.security.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

public class AuditRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity auditRevisionEntity = (AuditRevisionEntity) revisionEntity;
        
        // Set revision date
        auditRevisionEntity.setRevisionDate(new Date());
        
        // Set user information from security context
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && 
                !"anonymousUser".equals(authentication.getName())) {
                
                // Set username
                auditRevisionEntity.setUsername(authentication.getName());
                
                // Set user ID if available
                try {
                    Long userId = SecurityUtils.getUserId();
                    auditRevisionEntity.setUserId(userId);
                } catch (Exception e) {
                    // User ID not available, continue without it
                }
            }
        } catch (Exception e) {
            // Security context not available, continue without user info
        }
        
        // Set IP address if available
        try {
            ServletRequestAttributes attributes = 
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            
            String ipAddress = getClientIpAddress(request);
            auditRevisionEntity.setIpAddress(ipAddress);
        } catch (Exception e) {
            // Request context not available, continue without IP
        }
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null || xForwardedForHeader.isEmpty()) {
            return request.getRemoteAddr();
        }
        // X-Forwarded-For header can contain multiple IP addresses
        // The first one is the original client IP
        return xForwardedForHeader.split(",")[0].trim();
    }
}
