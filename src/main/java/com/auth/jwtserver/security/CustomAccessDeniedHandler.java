package com.auth.jwtserver.security;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException arg2) throws IOException, ServletException {
    	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    	String content = "{\r\n"
    			+ "    \"timestamp\": \"" + Instant.now() +"\",\r\n"
    			+ "    \"status\": 403,\r\n"
    			+ "    \"error\": \"Access Denied\",\r\n"
    			+ "    \"message\": \"You Do Not Have Permission To Access This Resouce\",\r\n"
    			+ "    \"data\": null\r\n"
    			+ "}";
    	response.setContentType("application/json");
    	response.setContentLength(content.length());
    	response.getWriter().write(content);
    }
}
