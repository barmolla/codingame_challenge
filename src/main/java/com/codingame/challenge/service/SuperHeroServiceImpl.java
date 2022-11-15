package com.codingame.challenge.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingame.challenge.dto.SuperHeroDTO;
import com.codingame.challenge.exception.ResourceAlreadyExistsException;
import com.codingame.challenge.exception.ResourceNotFoundException;
import com.codingame.challenge.repository.SuperHeroRepository;
import com.codingame.challenge.utils.SuperHeroMapper;

@Service
public class SuperHeroServiceImpl implements SuperHeroService {

	private static final Logger log = LoggerFactory.getLogger(SuperHeroServiceImpl.class);

	@Autowired
	private SuperHeroRepository superHeroRepository;

	@Autowired
	private SuperHeroMapper superHeroMapper;

	@Override
	public SuperHeroDTO create(SuperHeroDTO dto) {
		final var uuid = dto.getUuid();

		log.info("Seeking super hero with UUID ", uuid);

		final var found = this.superHeroRepository.findByUUID(uuid);

		if (found.isPresent()) throw new ResourceAlreadyExistsException("Super Hero with UUID" + uuid + " already exists.");

		final var entity = this.superHeroMapper.createFrom(dto);
		final var persisted = this.superHeroRepository.save(entity);
		final var mapped = this.superHeroMapper.createFrom(persisted);

		return mapped;
	}

	@Override
	public List<SuperHeroDTO> getAll() {
		final var entities = this.superHeroRepository
				.findAll()
				.stream()
				.filter(entity -> !entity.isDeleted())
				.collect(Collectors.toList());

		final var mapped = this.superHeroMapper.createFromEntities(entities);

		return mapped;
	}

	@Override
	public SuperHeroDTO update(SuperHeroDTO dto) throws ResourceNotFoundException {
		final var uuid = dto.getUuid();
		final var found = this.superHeroRepository
				.findByUUID(uuid)
				.filter(entity -> !entity.isDeleted())
				.orElseThrow(() -> this.getNewResourceNotFoundException(uuid));

		var updated = this.superHeroMapper.updateEntity(found, dto);		
		updated = this.superHeroRepository.save(found);

		return this.superHeroMapper.createFrom(updated);
	}

	@Override
	public SuperHeroDTO getByUUID(String uuid) throws ResourceNotFoundException {
		final var found = this.superHeroRepository
				.findByUUID(uuid)
				.filter(entity -> !entity.isDeleted())
				.orElseThrow(() -> this.getNewResourceNotFoundException(uuid));

		return this.superHeroMapper.createFrom(found);
	}

	@Override
	public boolean remove(String uuid) throws ResourceNotFoundException {
		final var found = this.superHeroRepository
			.findByUUID(uuid)
			.orElseThrow(() -> this.getNewResourceNotFoundException(uuid));

		if (found.isDeleted()) throw this.getNewResourceNotFoundException(uuid);

		found.setDeleted(true);

		this.superHeroRepository.save(found);

		return true;
	}

	private ResourceNotFoundException getNewResourceNotFoundException(String uuid) {
		return new ResourceNotFoundException("Super Hero with UUID" + uuid + " not found.");
	}

	@Override
	public List<SuperHeroDTO> findByName(String search) {
		final var entities = this.superHeroRepository.findByName(search);

		return this.superHeroMapper.createFromEntities(entities);
	}

}
