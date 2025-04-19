package com.neobank.neobankapi.controllers.dto;

public record TransactionRequest(String email, String password, double value) {

}
