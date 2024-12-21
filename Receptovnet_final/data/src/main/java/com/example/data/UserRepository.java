package com.example.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.domain.repository.UserRepositoryInterface;

public class UserRepository implements UserRepositoryInterface {
    private SharedPreferences sharedPreferences;

    public UserRepository(Context context) {
        sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public String getEmail() {
        return sharedPreferences.getString("email", "No Email");
    }

    @Override
    public String getUsername() {
        return sharedPreferences.getString("username", null);
    }

    @Override
    public void saveUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }
}
