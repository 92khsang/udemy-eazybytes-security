package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/secure").authenticated()
						.anyRequest().permitAll()
				)
				// Enables default form login
				.formLogin(Customizer.withDefaults())
				// Enables default OAuth2 login
				.oauth2Login(Customizer.withDefaults());

		return http.build();
	}
}
