package com.mirea.privalov.securesharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получаем SharedPreferences с помощью SecurePreferencesManager
        SharedPreferences sharedPreferences = SecurePreferencesManager.getSharedPreferences(this);

        // Пример использования SharedPreferences
        // Получаем значение из настроек
        String poetName = sharedPreferences.getString("poet_name", "Unknown");
        String favoriteMovie = sharedPreferences.getString("favorite_movie", "Unknown");

        // Отображаем значения на экране
        TextView poetNameTextView = findViewById(R.id.textViewPoetName);
        poetNameTextView.setText("Любимый поэт: " + poetName);

        TextView favoriteMovieTextView = findViewById(R.id.favoriteMovieTextView);
        favoriteMovieTextView.setText("Любимый фильм: " + favoriteMovie);

        // Находим кнопку сохранения
        Button saveButton = findViewById(R.id.buttonSave);
        // Устанавливаем обработчик нажатия кнопки
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем значения из полей ввода
                EditText poetNameEditText = findViewById(R.id.editTextPoetName);
                String poetName = poetNameEditText.getText().toString();

                EditText favoriteMovieEditText = findViewById(R.id.editTextFavoriteMovie);
                String favoriteMovie = favoriteMovieEditText.getText().toString();

                // Сохраняем значения в SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("poet_name", poetName);
                editor.putString("favorite_movie", favoriteMovie);
                editor.apply();
            }
        });
    }
}