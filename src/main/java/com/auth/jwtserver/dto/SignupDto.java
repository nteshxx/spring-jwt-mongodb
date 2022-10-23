package com.auth.jwtserver.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupDto {
    
	@NotBlank
    @Size(min = 3, max = 30)
    private String username;
    
	@NotBlank
    @Size(max = 60)
    @Email
    private String email;
    
	@NotBlank
    @Size(min = 6, max = 60)
    private String password;
	
}
