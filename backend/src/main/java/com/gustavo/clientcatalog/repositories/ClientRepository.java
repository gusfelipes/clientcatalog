package com.gustavo.clientcatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gustavo.clientcatalog.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
