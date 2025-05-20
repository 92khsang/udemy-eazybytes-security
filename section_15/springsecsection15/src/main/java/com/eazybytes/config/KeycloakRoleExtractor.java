package com.eazybytes.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class KeycloakRoleExtractor {

	public static Collection<GrantedAuthority> extract(Object realmAccessObj) {
		if (!(realmAccessObj instanceof Map<?, ?> realmAccess)) {
			return Collections.emptyList();
		}

		Object rolesObj = realmAccess.get("roles");

		if (!(rolesObj instanceof List<?> roles)) {
			return Collections.emptyList();
		}

		return roles.stream()
				.filter(String.class::isInstance)
				.map(String.class::cast)
				.map(roleName -> "ROLE_" + roleName.toUpperCase())
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
}
