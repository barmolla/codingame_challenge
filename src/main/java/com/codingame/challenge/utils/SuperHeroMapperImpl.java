package com.codingame.challenge.utils;

import org.springframework.stereotype.Component;

import com.codingame.challenge.dto.SuperHeroDTO;
import com.codingame.challenge.entity.SuperHero;

@Component
public class SuperHeroMapperImpl implements SuperHeroMapper {

	private static final long serialVersionUID = -3633552432852516165L;

	@Override
	public SuperHero createFrom(SuperHeroDTO dto) {
		final var entity = new SuperHero();

		entity.setUuid(dto.getUuid());
		entity.setName(dto.getName());

		return entity;
	}

	@Override
	public SuperHeroDTO createFrom(SuperHero entity) {
		final var dto = new SuperHeroDTO();

		dto.setUuid(entity.getUuid());
		dto.setName(entity.getName());

		return dto;
	}

	@Override
	public SuperHero updateEntity(SuperHero entity, SuperHeroDTO dto) {
		entity.setName(dto.getName());

		return entity;
	}

}
