package com.mirea.privalov.timeservice;

import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.mirea.privalov.timeservice.databinding.ActivityDisplayTimeBinding;

import android.os.Bundle;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DisplayTimeActivity extends AppCompatActivity {
    private TextView textViewTime;
    private TextView textViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_time);

        textViewTime = findViewById(R.id.textViewTime);
        textViewDate = findViewById(R.id.textViewDate);

        // Получаем строку времени из предыдущей активности
        String timeString = getIntent().getStringExtra("timeString");

        if (timeString != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.getDefault());
            try {

                // Первые 5 символов - это количество дней с 1900 года
                int daysSince1900 = Integer.parseInt(timeString.substring(0, 5));
                Calendar calendar = Calendar.getInstance();
                calendar.set(1900, Calendar.JANUARY, 1); // Устанавливаем 1 января 1900 года
                calendar.add(Calendar.DAY_OF_YEAR, daysSince1900); // Добавляем количество дней

                // Получаем дату и время из строки
                String dateString = timeString.substring(6, 23); // Обрезаем строку до последнего символа времени
                Date date = dateFormat.parse(dateString);

                textViewTime.setText("Текущее время: " + dateString.substring(0, 8));
                textViewDate.setText("Текущая дата: " + dateString.substring(9, 17));

                // Оставшаяся часть кода остается без изменений
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}