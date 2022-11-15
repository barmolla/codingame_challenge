package com.codingame.challenge.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTUserDetailsService implements UserDetailsService {

	private static final String HARDCODED_USERNAME = "brian";
	private static final String HARDCODED_ENCRYPTED_PASSWORD = "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (HARDCODED_USERNAME.equals(username)) {
			return new User(HARDCODED_USERNAME, HARDCODED_ENCRYPTED_PASSWORD, new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
