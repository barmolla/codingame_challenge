package com.codingame.challenge.dto;

import java.io.Serializable;

public class JWTRequestDTO implements Serializable {

	private static final long serialVersionUID = 2463112406751510530L;

	private String username;
	private String password;

	public JWTRequestDTO() {

	}

	public JWTRequestDTO(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}