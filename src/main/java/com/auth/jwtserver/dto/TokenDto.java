package com.auth.jwtserver.dto;

import javax.validation.constraints.NotBlank;

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
    
	@NotBlank
	private String refreshToken;
	
}
