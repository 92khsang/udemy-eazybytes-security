package com.eazybytes.config;

import java.util.Collections;

import com.eazybytes.exceptionhandling.CustomAccessDeniedHandler;
import com.eazybytes.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.eazybytes.filter.CsrfCookieFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

	@Bean
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
				// Disable CSRF for non-production profiles
				.csrf(csrfConfig ->csrfConfig
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
						.ignoringRequestMatchers("/contact", "/register")
				)
				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

				// Enforce plain HTTPS (not HTTP) in non-prod
				.requiresChannel(channel ->
						channel.anyRequest().requiresSecure())

				// Session management
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))

				// CORS setup
				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
					config.setAllowedMethods(Collections.singletonList("*"));
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setAllowCredentials(true);
					config.setMaxAge(3600L);
					return config;
				}))

				// Authorization rules
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/myAccount").hasRole("USER")
						.requestMatchers("/myLoans").hasRole("USER")
						.requestMatchers("/myCards").hasRole("USER")
						.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/user").authenticated()
						.requestMatchers("/notices", "/contact", "/error", "/register", "/invalidSession")
						.permitAll()
				)

				// Basic auth with custom entry point
				.httpBasic(httpBasic ->
						httpBasic.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()))

				// Custom access denied handler
				.exceptionHandling(exceptions ->
						exceptions.accessDeniedHandler(new CustomAccessDeniedHandler()));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public CompromisedPasswordChecker compromisedPasswordChecker() {
		return new HaveIBeenPwnedRestApiPasswordChecker();
	}
}
