package com.neobank.neobankapi.controllers.dto;

public record CreateTransactionRequest(String sender, String receiver, double value) {

}
