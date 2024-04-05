package com.mirea.privalov.mireaproject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BMIService extends Service {

    public class LocalBinder extends Binder {
        public BMIService getService() {
            return BMIService.this;
        }
    }

    private final IBinder binder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public double calculateBMI(double height, double weight) {
        // Проверка на деление на ноль
        if (height <= 0 || weight <= 0) {
            return 0.0;
        }

        // Переводим рост из сантиметров в метры
        double heightInMeters = height / 100.0;

        // Выводим значения роста и веса в логи
        Log.d("BMIService", "BMI: " + weight / (heightInMeters * heightInMeters));

        // Формула для расчета индекса массы тела (BMI)
        return weight / (heightInMeters * heightInMeters);
    }
}