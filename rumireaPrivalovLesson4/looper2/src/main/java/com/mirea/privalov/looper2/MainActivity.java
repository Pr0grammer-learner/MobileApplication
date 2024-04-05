package com.mirea.privalov.looper2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextAge;
    private EditText editTextJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAge = findViewById(R.id.editTextText);
        editTextJob = findViewById(R.id.editTextText2);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int age = Integer.parseInt(editTextAge.getText().toString());
                String job = editTextJob.getText().toString();

                MyLooper myLooper = new MyLooper();
                myLooper.startCalculation(age, job);
            }
        });
    }

    private class MyLooper extends Thread {
        private Handler mainThreadHandler;

        public MyLooper() {
            mainThreadHandler = new Handler(Looper.getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    Log.d("Looper", "Task execute. This is result: " + msg.getData().getString("result"));
                }
            };
        }

        public void startCalculation(final int age, final String job) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // Имитация вычислений с использованием времени, равного возрасту
                        Thread.sleep(age * 1000);

                        // Отправка сообщения с результатом в основной поток
                        Message message = mainThreadHandler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("result", "Ваш возраст: " + age + ", ваша профессия: " + job);
                        message.setData(bundle);
                        mainThreadHandler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}