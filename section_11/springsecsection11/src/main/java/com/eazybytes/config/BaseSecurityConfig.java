package com.eazybytes.config;

import java.util.Collections;

import com.eazybytes.exceptionhandling.CustomAccessDeniedHandler;
import com.eazybytes.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.eazybytes.filter.AuthoritiesLoggingAfterFilter;
import com.eazybytes.filter.AuthoritiesLoggingAtFilter;
import com.eazybytes.filter.CsrfCookieFilter;
import com.eazybytes.filter.JWTTokenGeneratorFilter;
import com.eazybytes.filter.JWTTokenValidatorFilter;
import com.eazybytes.filter.RequestValidationBeforeFilter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

public abstract class BaseSecurityConfig {

	protected void configureCommonSecurity(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf
						.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
						.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
						.ignoringRequestMatchers("/contact", "/register", "/apiLogin")
				)
				.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.cors(cors -> cors.configurationSource(request -> {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
					config.setAllowedMethods(Collections.singletonList("*"));
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setExposedHeaders(Collections.singletonList(HttpHeaders.AUTHORIZATION));
					config.setAllowCredentials(true);
					config.setMaxAge(3600L);
					return config;
				}))

				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/myAccount").hasRole("USER")
						.requestMatchers("/myLoans").hasRole("USER")
						.requestMatchers("/myCards").hasRole("USER")
						.requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
						.requestMatchers("/user").authenticated()
						.requestMatchers("/notices", "/contact", "/error", "/register", "/invalidSession", "/apiLogin")
						.permitAll()
				)

				.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
				.addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)

				.httpBasic(httpBasic -> httpBasic
						.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()))

				.exceptionHandling(exceptions -> exceptions
						.accessDeniedHandler(new CustomAccessDeniedHandler()));
	}
}