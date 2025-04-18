package com.neobank.neobankapi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.neobank.neobankapi.entities.Client;
import com.neobank.neobankapi.repository.ClientRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner{

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder){
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var userAdmin = clientRepository.findByEmail("admin");

        userAdmin.ifPresentOrElse(
            user -> {
                System.out.println("Admin ja existe");
            }, 
            ()-> {
                var  client = new Client();
                client.setName("admin");
                client.setEmail("admin");
                client.setPassword(passwordEncoder.encode("123"));
                client.setBalance(0);
                clientRepository.save(client);
            });
    }

}
