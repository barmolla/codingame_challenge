package com.codingame.challenge.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SuperHero extends BaseEntity {

	private static final long serialVersionUID = -3433398516068338044L;

	@Id
	private String uuid;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
