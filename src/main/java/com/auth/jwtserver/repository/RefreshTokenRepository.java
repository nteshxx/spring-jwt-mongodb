package com.auth.jwtserver.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.auth.jwtserver.document.RefreshToken;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
	
	void deleteByOwner_Id(ObjectId id);
    
	default void deleteByOwner_Id(String id) {
        deleteByOwner_Id(new ObjectId(id));
    };
    
}
