package com.example.favoritebook2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Получение данных из MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView ageView = findViewById(R.id.textView2);
            String university = extras.getString(MainActivity.KEY);
            ageView.setText(String.format("Мой любимая книга: %s", university));
        }
    }

    public void SendBook(View v) {
        EditText editTextBookName = findViewById(R.id.editTextText);
        Button buttonSend = findViewById(R.id.button2);

        String bookName = editTextBookName.getText().toString().trim();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.USER_MESSAGE, bookName);

        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}