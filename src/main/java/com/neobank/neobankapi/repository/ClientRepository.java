package com.neobank.neobankapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.neobank.neobankapi.entities.Client;

@Repository
public interface  ClientRepository extends JpaRepository<Client, UUID>{

}
