package com.example.common.security;

import com.example.demo.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtRolesGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String REALM_ACCESS_CLAIM_NAME = "realm_access";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        return Optional.ofNullable(jwt.getClaimAsMap(REALM_ACCESS_CLAIM_NAME))
                .map(realmAccess -> (Collection<String>) realmAccess.get(SecurityUtils.ROLES_CLAIM_NAME))
                .orElse(Collections.emptyList())
                .stream()
                .map(role -> new SimpleGrantedAuthority(CommonSecurityUtils.ROLE_PREFIX + role))
                .collect(Collectors.toList());
    }

}
