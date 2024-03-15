package com.example.toastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Artemiy_pupsik(View view){
        editTextText = findViewById(R.id.editTextText);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Toast СТУДЕНТ № 20 ГРУППА БСБО-11-21 Количество символов - " + editTextText.length(),
                Toast.LENGTH_SHORT);
        toast.show();
    }
}