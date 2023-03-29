package com.auth.jwtserver.security;

import java.io.IOException;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AccessTokenEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	String content = "{\r\n"
    			+ "    \"timestamp\": \"" + Instant.now() +"\",\r\n"
    			+ "    \"status\": " + HttpStatus.UNAUTHORIZED.value() + ",\r\n"
    			+ "    \"error\": \"Invalid Token\",\r\n"
    			+ "    \"message\": \"" + authException.getMessage() + "\",\r\n"
    			+ "    \"data\": null\r\n"
    			+ "}";
    	response.setContentType("application/json");
    	response.setContentLength(content.length());
    	response.getWriter().write(content);
    	
    }
}
