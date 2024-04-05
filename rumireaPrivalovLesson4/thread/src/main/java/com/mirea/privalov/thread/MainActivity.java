package com.mirea.privalov.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mirea.privalov.thread.databinding.ActivityMainBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAveragePairs();
            }
        });
    }

    private void calculateAveragePairs() {
        // Получаем значение количества пар и учебных дней из EditText
        int totalPairs = Integer.parseInt(binding.editTextText2.getText().toString());
        int totalDays = Integer.parseInt(binding.editTextText.getText().toString());

        // Отладочный вывод
        Log.d("CalculatePairs", "Total pairs: " + totalPairs);
        Log.d("CalculatePairs", "Total days: " + totalDays);

        // Создаем новый фоновый поток для расчетов
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Выполнение фоновых вычислений
                double averagePairsPerDay = (double) totalPairs / totalDays;
                sendDataToMainThread(averagePairsPerDay);
            }
        }).start(); // Запускаем фоновый поток
    }
    // Метод для передачи данных из фонового потока в основной поток
    private void sendDataToMainThread(double averagePairsPerDay) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Отображаем результат в TextView
                binding.textView.setText("Среднее кол-во пар в месяце: " + averagePairsPerDay);
            }
        });
    }
}