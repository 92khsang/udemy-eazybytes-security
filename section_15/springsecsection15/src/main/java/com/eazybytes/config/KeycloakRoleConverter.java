package com.eazybytes.config;

import java.util.Collection;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

	@Override
	public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
		Map<String, Object> claims = jwt.getClaims();
		Object realmAccessObj = claims.get("realm_access");
		return KeycloakRoleExtractor.extract(realmAccessObj);
	}
}
