package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("!prod")
public class ProjectSecurityConfig extends BaseSecurityConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		configureCommonSecurity(http);

		http.requiresChannel(channel -> channel
				.anyRequest().requiresInsecure());

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		AuthenticationProvider authProvider = new EazyBankUsernamePwdAuthenticationProvider(userDetailsService);
		ProviderManager providerManager = new ProviderManager(authProvider);
		providerManager.setEraseCredentialsAfterAuthentication(false);
		return providerManager;
	}

}
