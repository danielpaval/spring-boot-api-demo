package com.example.common.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.List;

public class CommonSecurityUtils {

    public static final String ROLE_PREFIX = "ROLE_";

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthenticated(Authentication authentication) {
        return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return isAuthenticated(authentication);
    }

    public static Jwt getJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken && isAuthenticated(authentication)) {
            return jwtAuthenticationToken.getToken();
        }
        return null;
    }

    public static <T> T getJwtClaim(String claim, Class<T> type) {
        Jwt jwt = getJwt();
        if (jwt != null) {
            Object value = jwt.getClaim(claim);
            if (value == null) {
                return null;
            }
            if (type.isInstance(value)) {
                return type.cast(value);
            }
            if (type == Long.class && value instanceof String) {
                return type.cast(Long.valueOf((String) value));
            }
            if (type == Integer.class && value instanceof String) {
                return type.cast(Integer.valueOf((String) value));
            }
        }
        return null;
    }

    public static String getJwtClaim(String claim) {
        return getJwtClaim(claim, String.class);
    }

    public static boolean isAuthorized(List<String> authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!isAuthenticated(authentication)) {
            return false;
        }
        if (authorities.isEmpty()) {
            return true;
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authorities::contains);
    }



}
