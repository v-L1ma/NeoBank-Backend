package com.neobank.neobankapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neobank.neobankapi.entities.Client;

@Repository
public interface  ClientRepository extends JpaRepository<Client, UUID>{

    Optional<Client> findByEmail(String email);
    Optional<Client> findByClientId(UUID id);

}
