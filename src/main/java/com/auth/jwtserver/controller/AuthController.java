package com.auth.jwtserver.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwtserver.document.RefreshToken;
import com.auth.jwtserver.document.User;
import com.auth.jwtserver.dto.LoginDto;
import com.auth.jwtserver.dto.SignupDto;
import com.auth.jwtserver.dto.TokenDto;
import com.auth.jwtserver.exception.IncorrectLoginCredentialsException;
import com.auth.jwtserver.exception.InvalidTokenException;
import com.auth.jwtserver.exception.UserAlreadyExistException;
import com.auth.jwtserver.jwt.JwtHelper;
import com.auth.jwtserver.repository.RefreshTokenRepository;
import com.auth.jwtserver.repository.UserRepository;
import com.auth.jwtserver.response.ResponseBuilder;
import com.auth.jwtserver.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
	@Autowired
    AuthenticationManager authenticationManager;
    
	@Autowired
    RefreshTokenRepository refreshTokenRepository;
    
	@Autowired
    UserRepository userRepository;
    
	@Autowired
    JwtHelper jwtHelper;
    
	@Autowired
    PasswordEncoder passwordEncoder;
    
	@Autowired
    UserService userService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto dto) {
    	try {
    		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
    		SecurityContextHolder.getContext().setAuthentication(authentication);
    		User user = (User) authentication.getPrincipal();

    		RefreshToken refreshToken = new RefreshToken();
    		refreshToken.setOwner(user);
    		refreshTokenRepository.save(refreshToken);

    		String accessToken = jwtHelper.generateAccessToken(user);
    		String refreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);

    		TokenDto responseData = new TokenDto(user.getId(), accessToken, refreshTokenString);
    		return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Logged In", responseData);
    	} catch (BadCredentialsException badCredEx) {
    		throw new IncorrectLoginCredentialsException();
    	}
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<Object> signup(@Valid @RequestBody SignupDto dto) {
        User user = new User(dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        
        try {
        	userRepository.save(user);
        } catch(DuplicateKeyException dupKeyEx) {
        	throw new UserAlreadyExistException();
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setOwner(user);
        refreshTokenRepository.save(refreshToken);

        String accessToken = jwtHelper.generateAccessToken(user);
        String refreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);

        TokenDto responseData = new TokenDto(user.getId(), accessToken, refreshTokenString);
        return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Signed Up", responseData);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestBody TokenDto dto) {
        String refreshTokenString = dto.getRefreshToken();
        if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            // valid and exists in db
            refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));

            return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Logged Out", null);
        }

        throw new InvalidTokenException();
    }

    @PostMapping("/logout-all")
    public ResponseEntity<Object> logoutAll(@RequestBody TokenDto dto) {
        String refreshTokenString = dto.getRefreshToken();
        if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            // valid and exists in db
            refreshTokenRepository.deleteByOwner_Id(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));

            return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Logged Out Of All Devices", null);
        }

        throw new InvalidTokenException();
    }

    @PostMapping("/access-token")
    public ResponseEntity<Object> accessToken(@RequestBody TokenDto dto) {
        String refreshTokenString = dto.getRefreshToken();
        if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            // valid and exists in db
            User user = userService.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
            String accessToken = jwtHelper.generateAccessToken(user);

            TokenDto responseData = new TokenDto(user.getId(), accessToken, refreshTokenString);
            return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Generated New Access Token", responseData);
        }

        throw new InvalidTokenException();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(@RequestBody TokenDto dto) {
        String refreshTokenString = dto.getRefreshToken();
        if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            // valid and exists in db
            refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));
            User user = userService.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));

            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setOwner(user);
            refreshTokenRepository.save(refreshToken);

            String accessToken = jwtHelper.generateAccessToken(user);
            String newRefreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);

            TokenDto responseData = new TokenDto(user.getId(), accessToken, newRefreshTokenString);
            return ResponseBuilder.build(HttpStatus.OK, null, "Successfully Generated New Refresh Token", responseData);
        }

        throw new InvalidTokenException();
    }
}
