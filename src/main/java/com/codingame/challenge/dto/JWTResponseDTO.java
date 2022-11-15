package com.codingame.challenge.dto;

import java.io.Serializable;

public class JWTResponseDTO implements Serializable {

	private static final long serialVersionUID = -1308336239337049403L;
	private final String JWTToken;

	public JWTResponseDTO(String JWTToken) {
		this.JWTToken = JWTToken;
	}

	public String getToken() {
		return this.JWTToken;
	}
}