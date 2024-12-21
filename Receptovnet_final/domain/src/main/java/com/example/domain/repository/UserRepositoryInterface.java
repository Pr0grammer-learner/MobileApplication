package com.example.domain.repository;

public interface UserRepositoryInterface {
    String getEmail();
    String getUsername();
    void saveUsername(String username);
}

