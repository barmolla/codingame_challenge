package com.codingame.challenge.utils;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.codingame.challenge.dto.BaseDTO;
import com.codingame.challenge.entity.BaseEntity;

/**
 * 
 * @author Brian Armolla (brian.armolla@gmail.com)
 * @param <E> Any class that extends of {@link BaseEntity}
 * @param <D> Any class that extends of {@link BaseDTO}
 */
public interface GenericMapper<ENTITY extends BaseEntity, DTO extends BaseDTO> extends Serializable {

	ENTITY createFrom(final DTO dto);

	DTO createFrom(final ENTITY entity);
 
	ENTITY updateEntity(final ENTITY entity, final DTO dto);
 
	default List<DTO> createFromEntities(final List<ENTITY> entities) {
		return entities
				.stream()
				.map(this::createFrom)
				.collect(Collectors.toList());
	}
 
	default List<ENTITY> createFromDtos(final List<DTO> dtos) {
		return dtos
				.stream()
				.map(this::createFrom)
				.collect(Collectors.toList());
	}

}
