package com.codingame.challenge.unit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.codingame.challenge.dto.SuperHeroDTO;
import com.codingame.challenge.entity.SuperHero;
import com.codingame.challenge.utils.SuperHeroMapperImpl;

public class SuperHeroMapperUnitTest {

	private final SuperHeroMapperImpl superHeroMapper;

	public SuperHeroMapperUnitTest() {
		this.superHeroMapper = new SuperHeroMapperImpl();
	}

	@Test
	void givenADTOWhenCreateFromThenReturnEntity() {
		final var dto = new SuperHeroDTO();
		final var uuid = "2d46131e-6453-11ed-81ce-0242ac120002";
		final var name = "Thor";

		dto.setUuid(uuid);
		dto.setName(name);

		final var entity = this.superHeroMapper.createFrom(dto);

		assertAll("Should return an Entity",
				() -> assertNotNull(entity),
				() -> assertEquals(uuid, entity.getUuid()),
				() -> assertEquals(name, entity.getName()));
	}

	@Test
	void givenAnEntityWhenCreateFromThenReturnDTO() {
		final var entity = new SuperHero();
		final var uuid = "2d46131e-6453-11ed-81ce-0242ac120003";
		final var name = "Capitán América";

		entity.setUuid(uuid);
		entity.setName(name);

		final var dto = this.superHeroMapper.createFrom(entity);

		assertAll("Should return a DTO",
				() -> assertNotNull(dto),
				() -> assertEquals(uuid, dto.getUuid()),
				() -> assertEquals(name, dto.getName()));
	}

	@Test
	void givenDTOArrayWhenCreateFromDTOsThenReturnEntitiesArray() {
		List<SuperHero> superHeroEntitiesList = this.superHeroMapper.createFromDtos(this.getSuperHeroDTOArray());

		assertAll("Should return a valid super hero DTO list",
				() -> assertNotNull(superHeroEntitiesList),
				() -> assertEquals(3, superHeroEntitiesList.size()),
				() -> assertEquals("Capitán América", superHeroEntitiesList.get(0).getName()),
				() -> assertEquals("Superman", superHeroEntitiesList.get(2).getName()));
	}

	@Test
	void givenEntityArrayWhenCreateFromEntitiessThenReturnDTOArray() {
		List<SuperHeroDTO> superHeroDTOList = this.superHeroMapper.createFromEntities(this.getSuperHeroEntityArray());

		assertAll("Should return a valid super hero DTO list",
				() -> assertNotNull(superHeroDTOList),
				() -> assertEquals(3, superHeroDTOList.size()),
				() -> assertEquals("Capitán América", superHeroDTOList.get(0).getName()),
				() -> assertEquals("Superman", superHeroDTOList.get(2).getName()));
	}

	private List<SuperHeroDTO> getSuperHeroDTOArray() {
		final var superHero1 = new SuperHeroDTO();
		final var superHero2 = new SuperHeroDTO();
		final var superHero3 = new SuperHeroDTO();

		superHero1.setUuid("2d46131e-6453-11ed-81ce-0242ac120001");
		superHero2.setUuid("2d46131e-6453-11ed-81ce-0242ac120002");
		superHero3.setUuid("2d46131e-6453-11ed-81ce-0242ac120003");
		superHero1.setName("Capitán América");
		superHero2.setName("Thor");
		superHero3.setName("Superman");

		return Arrays.asList(superHero1, superHero2, superHero3);
	}

	private List<SuperHero> getSuperHeroEntityArray() {
		final var superHero1 = new SuperHero();
		final var superHero2 = new SuperHero();
		final var superHero3 = new SuperHero();

		superHero1.setUuid("2d46131e-6453-11ed-81ce-0242ac120001");
		superHero2.setUuid("2d46131e-6453-11ed-81ce-0242ac120002");
		superHero3.setUuid("2d46131e-6453-11ed-81ce-0242ac120003");
		superHero1.setName("Capitán América");
		superHero2.setName("Thor");
		superHero3.setName("Superman");

		return Arrays.asList(superHero1, superHero2, superHero3);
	}
}
