package com.codingame.challenge.service;

import java.util.List;

import com.codingame.challenge.dto.SuperHeroDTO;

public interface SuperHeroService extends GenericService<SuperHeroDTO> {

	List<SuperHeroDTO> findByName(String search);
}