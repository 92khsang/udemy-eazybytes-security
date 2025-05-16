package com.eazybytes.exceptionhandling;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		response.setHeader("eazybank-error-reason", "Authentication failed");
		response.setContentType("application/json;charset=UTF-8");

		// Construct the JSON response
		String jsonResponse = constructJsonResponse(request, authException);

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(jsonResponse);
	}

	private String constructJsonResponse(HttpServletRequest request, AuthenticationException authException) {
		LocalDateTime currentTimeStamp = LocalDateTime.now();
		String message = (authException != null && authException.getMessage() != null) ? authException.getMessage()
				: "Unauthorized";
		String path = request.getRequestURI();

		return String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
				currentTimeStamp, HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(),
				message, path);
	}
}
