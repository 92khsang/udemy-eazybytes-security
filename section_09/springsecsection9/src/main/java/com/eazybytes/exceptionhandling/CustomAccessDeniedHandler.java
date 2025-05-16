package com.eazybytes.exceptionhandling;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// Populate dynamic values
		response.setHeader("eazybank-denied-reason", "Authorization failed");
		response.setStatus(HttpStatus.FORBIDDEN.value());

		// Construct the JSON response
		String jsonResponse = constructJsonResponse(request, accessDeniedException);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(jsonResponse);
	}

	private String constructJsonResponse(HttpServletRequest request, AccessDeniedException accessDeniedException) {
		LocalDateTime currentTimeStamp = LocalDateTime.now();
		String message = (accessDeniedException != null && accessDeniedException.getMessage() != null) ?
				accessDeniedException.getMessage() : "Authorization failed";
		String path = request.getRequestURI();
		return String.format("{\"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\"}",
				currentTimeStamp, HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(),
				message, path);
	}
}
