package com.neobank.neobankapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.neobankapi.entities.Transaction;
import com.neobank.neobankapi.repository.ClientRepository;
import com.neobank.neobankapi.repository.TransactionRepository;

@RestController
public class GetTransactions {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    public GetTransactions(TransactionRepository transactionRepository, ClientRepository clientRepository){
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity<List<Transaction>> listTransactions(@PathVariable("id") String id){

        var clientFromDb = clientRepository.findByClientId(id);

        if(clientFromDb.isEmpty()){
            throw new BadCredentialsException("Por favor, insira uma conta válida!");
        }

        var transactions = transactionRepository.findBySender_ClientId(id);

        return ResponseEntity.ok(transactions);
    }

}
