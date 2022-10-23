package com.auth.jwtserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDto {
    
	private String userId;
    
	private String accessToken;
    
	private String refreshToken;
	
}
