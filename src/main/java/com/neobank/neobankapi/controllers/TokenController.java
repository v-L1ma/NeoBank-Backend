package com.neobank.neobankapi.controllers;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.neobank.neobankapi.controllers.dto.LoginRequest;
import com.neobank.neobankapi.controllers.dto.LoginResponse;
import com.neobank.neobankapi.repository.ClientRepository;

@RestController
public class TokenController {

    private final JwtEncoder jwtEncoder;
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenController(JwtEncoder jwtEncoder, ClientRepository clientRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.jwtEncoder = jwtEncoder;
        this.clientRepository = clientRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        var client = clientRepository.findByEmail(loginRequest.email());

        if(client.isEmpty() && client.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)){
            throw new BadCredentialsException("Usuário ou senha inválidos!");
        }

        var now = Instant.now();
        var expiresIn = 300L;


        var claims = JwtClaimsSet.builder()
                    .issuer("NeoBank")
                    .subject(client.get().getClientId().toString())
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(expiresIn))
                    .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));

    }

}
