package com.auth.jwtserver.security;

import java.io.IOException;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
    	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    	String content = "{\r\n"
    			+ "    \"timestamp\": \"" + Instant.now() +"\",\r\n"
    			+ "    \"status\": " + HttpStatus.FORBIDDEN.value() + ",\r\n"
    			+ "    \"error\": \"You Don't Have Permission To Access This Resouce\",\r\n"
    			+ "    \"message\": \"" + ex.getMessage() + "\",\r\n"
    			+ "    \"data\": null\r\n"
    			+ "}";
    	response.setContentType("application/json");
    	response.setContentLength(content.length());
    	response.getWriter().write(content);
    }
}
