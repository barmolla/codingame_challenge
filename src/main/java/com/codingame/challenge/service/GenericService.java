package com.codingame.challenge.service;

import java.util.List;

import com.codingame.challenge.dto.BaseDTO;
import com.codingame.challenge.exception.ResourceAlreadyExistsException;
import com.codingame.challenge.exception.ResourceNotFoundException;

public interface GenericService<DTO extends BaseDTO> {

	List<DTO> getAll();

	DTO update(DTO dto) throws ResourceNotFoundException;

	DTO create(DTO dto) throws ResourceAlreadyExistsException;

	DTO getByUUID(String uuid) throws ResourceNotFoundException;

	boolean remove(String uuid) throws ResourceNotFoundException;
}
