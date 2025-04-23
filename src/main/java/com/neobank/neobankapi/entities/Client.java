package com.neobank.neobankapi.entities;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.neobank.neobankapi.controllers.dto.LoginRequest;
import com.neobank.neobankapi.controllers.dto.TransactionRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_clients")
public class Client {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID clientId;

    private String name;

    @Column(unique=true)
    private String email;

    private String password;

    private double balance;

    public UUID getClientId() {
        return clientId;
    }

    public void setClientId(UUID clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double value) {
        this.balance += value;
    }

    public boolean isLoginCorrect(LoginRequest loginRequest, PasswordEncoder passwordEncoder){
        return  passwordEncoder.matches(loginRequest.password(), this.password);

    }

    public boolean isTransactionValid(TransactionRequest withdrawRequest, PasswordEncoder passwordEncoder){
        return  passwordEncoder.matches(withdrawRequest.password(), this.password);
    }

}