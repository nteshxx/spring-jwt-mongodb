package com.auth.jwtserver.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwtserver.dto.LoginDto;
import com.auth.jwtserver.dto.SignupDto;
import com.auth.jwtserver.dto.TokenDto;
import com.auth.jwtserver.service.AuthService;
import com.auth.jwtserver.utility.ResponseBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
	@Autowired
	AuthService authService;
	
	@PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody SignupDto dto) {
        TokenDto responseData = authService.signup(dto);
        return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Signed Up", responseData);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto dto) {
    	TokenDto data = authService.login(dto);
    	return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Logged In", data);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody TokenDto dto) {
        authService.logout(dto);
        return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Logged Out", null);
    }

    @PostMapping("/logout-all")
    public ResponseEntity<Object> logoutAll(@RequestBody TokenDto dto) {
        authService.logoutAll(dto);
    	return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Logged Out Of All Devices", null);
    }

    @PostMapping("/access-token")
    public ResponseEntity<Object> accessToken(@RequestBody TokenDto dto) {
        TokenDto data = authService.accessToken(dto);
        return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Generated New Access Token", data);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(@RequestBody TokenDto dto) {
        TokenDto data = authService.refreshToken(dto);
        return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Generated New Refresh Token", data);
    }
}
