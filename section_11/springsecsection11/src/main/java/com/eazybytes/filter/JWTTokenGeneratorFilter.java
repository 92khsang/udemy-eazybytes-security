package com.eazybytes.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import com.eazybytes.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			Environment env = getEnvironment();
			String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
			SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

			Date now = new Date();

			String jwt = Jwts.builder().issuer("Eazy Bank").subject("JWT Token")
					.claim("username", authentication.getName())
					.claim("authorities", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
							.collect(Collectors.joining(",")))
					.issuedAt(now)
					.expiration(new Date(now.getTime() + 30000000))
					.signWith(secretKey)
					.compact();

			response.setHeader(HttpHeaders.AUTHORIZATION, jwt);
		}

		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return !request.getServletPath().equals("/user");
	}
}
