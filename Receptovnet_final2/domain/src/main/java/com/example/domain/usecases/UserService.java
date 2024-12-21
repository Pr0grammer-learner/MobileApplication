package com.example.domain.usecases;

import com.example.domain.repository.UserServiceInterface;

public class UserService implements UserServiceInterface {
    @Override
    public String generateRandomUsername() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomUsername = new StringBuilder();
        int length = 6; // Длина случайного логина

        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            randomUsername.append(characters.charAt(index));
        }

        return randomUsername.toString();
    }
}


