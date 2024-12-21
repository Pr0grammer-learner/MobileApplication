package com.example.domain.usecases;

import com.example.domain.repository.AuthRepository;

public class LoginUser {
    private final AuthRepository authRepository;

    public LoginUser(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String email, String password, AuthRepository.AuthCallback callback) {
        authRepository.login(email, password, callback);
    }
}
