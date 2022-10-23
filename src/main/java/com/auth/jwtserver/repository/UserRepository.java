package com.auth.jwtserver.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.auth.jwtserver.document.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByUsername(String username);
	
}
