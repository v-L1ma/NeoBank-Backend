package com.neobank.neobankapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.neobank.neobankapi.controllers.dto.CreateClientDto;
import com.neobank.neobankapi.entities.Client;
import com.neobank.neobankapi.repository.ClientRepository;

@RestController
public class CreateClientController {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CreateClientController(ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder){
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @PostMapping("/signup")
    public ResponseEntity<Void> newClient(@RequestBody CreateClientDto dto){

        var clientFromDB = clientRepository.findByEmail(dto.email());

        if(clientFromDB.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        var client = new Client();
        client.setName(dto.name());
        client.setEmail(dto.email());
        client.setPassword(passwordEncoder.encode(dto.password()));
        client.setBalance(0.0);
        clientRepository.save(client);

        return ResponseEntity.ok().build();

    }


}
