package com.neobank.neobankapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.neobank.neobankapi.controllers.dto.TransactionRequest;
import com.neobank.neobankapi.repository.ClientRepository;

@RestController
public class WithdrawController {

    private final ClientRepository clientRepository;    
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WithdrawController(ClientRepository clientRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.clientRepository = clientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody TransactionRequest dto){

        var clientFromDB = clientRepository.findByEmail(dto.email());
        var balance = clientFromDB.get().getBalance();

        if(clientFromDB.isEmpty() || !clientFromDB.get().isTransactionValid(dto, bCryptPasswordEncoder)){
            throw new BadCredentialsException("Por favor, insira uma conta válida!");
        }

        if(balance<dto.value()){
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Saldo insuficiente!");
        }

        clientFromDB.get().setBalance(-dto.value());
        clientRepository.save(clientFromDB.get());

        return ResponseEntity.ok().build();
    }

}
