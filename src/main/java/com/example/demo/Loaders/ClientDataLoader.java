package com.example.demo.Loaders;

import com.example.demo.Entity.UserEntity;
import com.example.demo.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientDataLoader implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public ClientDataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 10; i++) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(UUID.randomUUID());
            userEntity.setName("Client Name " + i);
            userEntity.setDni(String.format("%08d", i * 12345));
            userEntity.setEmail("client" + i + "@example.com");

            userService.saveUser(userEntity);
        }

        System.out.println("10 Clients populated successfully!");
    }
}