package com.neobank.neobankapi.controllers.dto;

public record LoginResponse(String accessToken, Long expiresIn) {}
