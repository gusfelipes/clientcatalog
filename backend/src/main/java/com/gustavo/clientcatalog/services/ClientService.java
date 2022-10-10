package com.gustavo.clientcatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gustavo.clientcatalog.dto.ClientDTO;
import com.gustavo.clientcatalog.entities.Client;
import com.gustavo.clientcatalog.repositories.ClientRepository;
import com.gustavo.clientcatalog.services.exceptions.DatabaseException;
import com.gustavo.clientcatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(entity -> new ClientDTO(entity));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> object = repository.findById(id);
		Client entity = object.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));

		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		copyDTOToEntity(dto, entity);
		entity = repository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getReferenceById(id);
			copyDTOToEntity(dto, entity);
			;
			entity = repository.save(entity);
			return new ClientDTO(entity);

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("id not found: " + id);
		}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);

		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("id not found: " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Database violation");
		}
	}

	private void copyDTOToEntity(ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());

	}
}
