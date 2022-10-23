package com.auth.jwtserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth.jwtserver.document.User;
import com.auth.jwtserver.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}
	
	public User findById(String id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User Id not found"));
	}

}
