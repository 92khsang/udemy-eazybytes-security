package com.eazybytes.config;

import java.util.Collection;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;

public class KeycloakOpaqueRoleConverter implements OpaqueTokenAuthenticationConverter {

	@Override
	public Authentication convert(@NonNull String introspectedToken, @NonNull OAuth2AuthenticatedPrincipal principal) {
		Collection<GrantedAuthority> authorities = extractAuthorities(principal);

		return new UsernamePasswordAuthenticationToken(principal.getName(), null, authorities);
	}

	private Collection<GrantedAuthority> extractAuthorities(OAuth2AuthenticatedPrincipal principal) {
		Object rolesObj = principal.getAttribute("scope");
		return KeycloakRoleExtractor.extract(rolesObj);
	}
}
