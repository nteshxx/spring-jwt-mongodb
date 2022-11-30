package com.auth.jwtserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    
	private String userId;
    
	private String accessToken;
    
	private String refreshToken;
	
}
