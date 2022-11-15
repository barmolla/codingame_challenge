package com.codingame.challenge.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingame.challenge.dto.SuperHeroDTO;
import com.codingame.challenge.entity.SuperHero;
import com.codingame.challenge.exception.ResourceNotFoundException;
import com.codingame.challenge.service.SuperHeroService;
import com.codingame.challenge.utils.MeasureTime;

@RestController
@RequestMapping("/api/v1")
public class SuperHeroController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SuperHeroService superHeroService;

	/**
	 * Create a super hero.
	 * 
	 * @param superHeroDTO
	 * @return
	 */
	@MeasureTime
	@PostMapping(
			value 	 = "/super-hero",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuperHeroDTO> create(/*@Valid*/ @RequestBody SuperHeroDTO superHeroDTO) {
		logger.info("Creating a super hero");

		return ResponseEntity.status(HttpStatus.CREATED).body(this.superHeroService.create(superHeroDTO));
	}

	/**
	 * Get all super heroes.
	 * 
	 * @return the list
	 */
	@MeasureTime
	@GetMapping(
			value = "/super-hero",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SuperHeroDTO>> getAll() {
		logger.info("getting all super heroes");

		return ResponseEntity
				.ok()
				.body(this.superHeroService.getAll());
	}

	/**
	 * Get super hero by uuid.
	 *
	 * @param uuid the super hero uuid
	 * @return the super hero found
	 * @throws ResourceNotFoundException the resource not found.
	 */
	@MeasureTime
	@GetMapping(
			value = "/super-hero/{uuid}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuperHeroDTO> getByUUID(@PathVariable(value = "uuid") String uuid) {
		return ResponseEntity
				.ok()
				.body(this.superHeroService.getByUUID(uuid));
	}

	/**
	 * Delete super hero.
	 *
	 * @param uuid the super hero uuid
	 * @throws ResourceNotFoundException the resource not found.
	 */
	@MeasureTime
	@DeleteMapping(
			value = "/super-hero/{uuid}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> remove(@PathVariable(value = "uuid") String uuid) {
		this.superHeroService.remove(uuid);

		return ResponseEntity
				.ok()
				.build();
	}

	/**
	 * Find super hero by name containing the word received as a parameter.
	 * 
	 * @param search the word to find in name
	 * @return matched results.
	 */
	@MeasureTime
	@GetMapping(
			value = "/super-hero/search",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SuperHeroDTO>> findByName(@RequestParam(value = "q") String search) {
		return ResponseEntity
				.ok()
				.body(this.superHeroService.findByName(search));
	}

	/**
	 * Update super hero entity.
	 * Partial modification of {@link SuperHero} resource.
	 *
	 * @param uuid the super hero uuid
	 * @param patch changes
	 * @throws ResourceNotFoundException the resource not found exception
	 */
	@MeasureTime
	@PatchMapping(
			value = "/super-hero/{uuid}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> partialUpdate(
			@PathVariable(value = "uuid") String uuid, @RequestBody SuperHeroDTO patch) {

		patch.setUuid(uuid);

		this.superHeroService.update(patch);

		return ResponseEntity
				.noContent()
				.build();
	}

}
