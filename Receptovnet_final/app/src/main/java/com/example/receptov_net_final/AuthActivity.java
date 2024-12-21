package com.example.receptov_net_final;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.data.FirebaseAuthRepository;
import com.example.domain.repository.AuthRepository;

public class AuthActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton, guestLoginButton;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        guestLoginButton = findViewById(R.id.guest_login_button);

        AuthRepository authRepository = new FirebaseAuthRepository();
        authViewModel = new AuthViewModel(authRepository);

        // Наблюдение за состоянием успешного логина
        authViewModel.getLoginSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                String email = emailEditText.getText().toString();
                saveUserData(email);  // Сохранение данных пользователя
                Toast.makeText(AuthActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                // Перенаправление в MainActivity
                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                finish();  // Закрытие текущей активности
            }
        });


        // Наблюдение за сообщением об ошибке
        authViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(AuthActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Логика для авторизации
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Проверка на пустые поля
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(AuthActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Выполнение логина
            authViewModel.login(email, password);
        });

        // Логика для перехода на страницу регистрации
        registerButton.setOnClickListener(v -> {
            // Открытие страницы регистрации
            Intent intent = new Intent(AuthActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        guestLoginButton.setOnClickListener(v -> {
            Toast.makeText(AuthActivity.this, "Logged in as guest", Toast.LENGTH_SHORT).show();

            // Перенаправление в MainActivity
            startActivity(new Intent(AuthActivity.this, MainActivity.class));
            finish();  // Закрытие текущей активности
        });
    }

    private void saveUserData(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);  // Сохраняем email
        editor.putBoolean("isLoggedIn", true);  // Устанавливаем флаг, что пользователь авторизован
        editor.apply();
    }
}
