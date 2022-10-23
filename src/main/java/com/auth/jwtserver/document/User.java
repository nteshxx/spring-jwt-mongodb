package com.auth.jwtserver.document;

import java.util.Collection;
import java.util.Collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NonNull;

@Document
@Data
public class User implements UserDetails {
	
	private static final long serialVersionUID = 8857553857057109075L;

	@Id
    private String id;

	@Indexed(unique = true)
    @NonNull
    private String username;
	
	@Indexed(unique = true)
    @NonNull
    private String email;

	@JsonIgnore
    @NonNull
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
    
    @Override
    public String getPassword() {
    	return password;
    }
    
    @Override
    public String getUsername() {
    	return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
