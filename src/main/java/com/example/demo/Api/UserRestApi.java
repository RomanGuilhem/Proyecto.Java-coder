package com.example.demo.Api;

import com.example.demo.Entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserRestApi {

    private final RestTemplate restTemplate;

    public UserRestApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<UserEntity[]> getUsers() {
        String url = "http://localhost:8080/api/users";
        return restTemplate.getForEntity(url, UserEntity[].class);
    }

    public UserEntity getUserById(int id) {
        String url = "http://localhost:8080/api/users/" + id;
        return restTemplate.getForObject(url, UserEntity.class);
    }

    public UserEntity saveUser(UserEntity user) {
        String url = "http://localhost:8080/api/users";
        return restTemplate.postForObject(url, user, UserEntity.class);
    }

    public UserEntity updateUser(int id, UserEntity user) {
        String url = "http://localhost:8080/api/users/" + id;
        restTemplate.put(url, user);
        return user;
    }

    public void deleteUser(int id) {
        String url = "http://localhost:8080/api/users/" + id;
        restTemplate.delete(url);
    }
}
