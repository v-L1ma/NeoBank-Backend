package com.neobank.neobankapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.neobank.neobankapi.controllers.dto.DepositRequest;
import com.neobank.neobankapi.repository.ClientRepository;

@RestController
public class DepositController {

    private final ClientRepository clientRepository;

    public DepositController(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody DepositRequest dto){

        var clientFromDB = clientRepository.findByEmail(dto.email());

        if (clientFromDB.isEmpty()) {
            throw new BadCredentialsException("Conta inexistente!");
        }

        if (dto.value()<0) {
            throw new  ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }

        clientFromDB.get().setBalance(dto.value());
        clientRepository.save(clientFromDB.get()); 

        return ResponseEntity.ok().build();

    }

}
