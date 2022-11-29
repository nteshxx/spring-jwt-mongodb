package com.auth.jwtserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwtserver.document.User;
import com.auth.jwtserver.service.UserService;
import com.auth.jwtserver.utility.ResponseBuilder;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
	@Autowired
    UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<Object> getUserProfile(@AuthenticationPrincipal User user) {
    	return ResponseBuilder.build(HttpStatus.OK, null, "Success", user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#user.id == #id")
    public ResponseEntity<Object> getUserById(@AuthenticationPrincipal User user, @PathVariable String id) {
    	User userProfile = userService.findById(id);
    	return ResponseBuilder.build(HttpStatus.OK, null, "Success", userProfile);
    }
}
