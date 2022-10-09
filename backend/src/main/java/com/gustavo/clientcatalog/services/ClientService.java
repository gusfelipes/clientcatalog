package com.gustavo.clientcatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gustavo.clientcatalog.dto.ClientDTO;
import com.gustavo.clientcatalog.entities.Client;
import com.gustavo.clientcatalog.repositories.ClientRepository;
import com.gustavo.clientcatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest){
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(entity -> new ClientDTO(entity));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> object = repository.findById(id);
		Client entity = object.orElseThrow(()-> new ResourceNotFoundException("Entity not found"));
		
		return new ClientDTO(entity);
	}
	

}
