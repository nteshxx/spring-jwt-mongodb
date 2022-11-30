package com.auth.jwtserver.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class RefreshToken {

	@Id
	private String id;
	
	@DocumentReference
	private User owner;
	
}
