package com.mirea.privalov.rumireaprivalovlesson6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextGroup;
    private EditText editTextNumber;
    private EditText editTextMovie;
    private Button buttonSave;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextGroup = findViewById(R.id.editTextGroup);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextMovie = findViewById(R.id.editTextMovie);
        buttonSave = findViewById(R.id.buttonSave);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Загрузка сохраненных значений
        editTextGroup.setText(sharedPreferences.getString("group", ""));
        editTextNumber.setText(sharedPreferences.getString("number", ""));
        editTextMovie.setText(sharedPreferences.getString("movie", ""));

        buttonSave.setOnClickListener(v -> {
            // Сохранение значений при нажатии кнопки
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("group", editTextGroup.getText().toString());
            editor.putString("number", editTextNumber.getText().toString());
            editor.putString("movie", editTextMovie.getText().toString());
            editor.apply();
        });
    }
}