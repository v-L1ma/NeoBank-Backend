package com.neobank.neobankapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.neobank.neobankapi.controllers.dto.CreateTransactionRequest;
import com.neobank.neobankapi.entities.Transaction;
import com.neobank.neobankapi.repository.ClientRepository;
import com.neobank.neobankapi.repository.TransactionRepository;

@RestController
public class CreateTransaction {

    private final TransactionRepository transactionRepository;
    private final ClientRepository clientRepository;

    public CreateTransaction(TransactionRepository transactionRepository, ClientRepository clientRepository){
        this.transactionRepository = transactionRepository;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/transaction")
    public ResponseEntity<Void> transaction(@RequestBody CreateTransactionRequest dto){

        var senderFromDB = clientRepository.findByEmail(dto.sender());
        var receiverFromDB = clientRepository.findByEmail(dto.receiver());

        boolean hasEnougthBalance = senderFromDB.get().getBalance() >= dto.value();

        if(senderFromDB.isEmpty() || receiverFromDB.isEmpty()){
            throw new BadCredentialsException("Por favor, informe contas existentes.");
        }

        if(!hasEnougthBalance || dto.value()<0){
             throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }

        var transaction = new Transaction();
        transaction.setSender(senderFromDB.get());
        transaction.setReceiver(receiverFromDB.get());
        transaction.setValue(dto.value());
        receiverFromDB.get().setBalance(dto.value());
        senderFromDB.get().setBalance(-dto.value());
        transactionRepository.save(transaction);

        return ResponseEntity.ok().build();
        //ver se o sender existe, ver se o receiver existe, 
        //ver se o saldo do sender é maior que o valor a enviar

    }

}
