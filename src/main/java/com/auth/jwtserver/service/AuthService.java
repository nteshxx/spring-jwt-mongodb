package com.auth.jwtserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth.jwtserver.document.RefreshToken;
import com.auth.jwtserver.document.User;
import com.auth.jwtserver.dto.LoginDto;
import com.auth.jwtserver.dto.SignupDto;
import com.auth.jwtserver.dto.TokenDto;
import com.auth.jwtserver.exception.IncorrectLoginCredentialsException;
import com.auth.jwtserver.exception.InvalidTokenException;
import com.auth.jwtserver.repository.RefreshTokenRepository;
import com.auth.jwtserver.repository.UserRepository;
import com.auth.jwtserver.utility.JwtHelper;

@Service
public class AuthService {

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

    @Transactional
    public TokenDto signup(SignupDto dto) {
        User user = new User(dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setOwner(user);
        refreshTokenRepository.save(refreshToken);

        String accessToken = jwtHelper.generateAccessToken(user);
        String refreshTokenString = jwtHelper.generateRefreshToken(user, refreshToken);

        TokenDto responseData = new TokenDto(user.getId(), accessToken, refreshTokenString);
        return responseData;
    }

    @Transactional
    public TokenDto login(LoginDto dto) {
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
    		return responseData;
    	} catch (BadCredentialsException badCredEx) {
    		throw new IncorrectLoginCredentialsException();
    	}
    }
    
    public void logout(TokenDto dto) {
        String refreshTokenString = dto.getRefreshToken();
        if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            // valid and exists in db
            refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));
            return;
        }

        throw new InvalidTokenException();
    }

    public void logoutAll(TokenDto dto) {
        String refreshTokenString = dto.getRefreshToken();
        if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            // valid and exists in db
            refreshTokenRepository.deleteByOwner_Id(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
            return;
        }

        throw new InvalidTokenException();
    }

    public TokenDto accessToken(TokenDto dto) {
        String refreshTokenString = dto.getRefreshToken();
        if (jwtHelper.validateRefreshToken(refreshTokenString) && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            // valid and exists in db
            User user = userService.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
            String accessToken = jwtHelper.generateAccessToken(user);

            TokenDto responseData = new TokenDto(user.getId(), accessToken, refreshTokenString);
            return responseData;
        }
        
        throw new InvalidTokenException();
    }

    public TokenDto refreshToken(TokenDto dto) {
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
            return responseData;
        }
        
        throw new InvalidTokenException();
    }
}
