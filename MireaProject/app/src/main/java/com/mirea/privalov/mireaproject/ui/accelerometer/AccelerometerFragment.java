package com.mirea.privalov.mireaproject.ui.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.Manifest;
import com.mirea.privalov.mireaproject.R;

import java.util.Locale;


public class AccelerometerFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private TextView stepsCountTextView;

    private static final int REQUEST_PERMISSION_CODE = 200;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(); // Запрос разрешений при создании фрагмента

        // Получение менеджера датчиков
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        // Получение датчика шагов
        if (sensorManager != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Регистрация слушателя датчика
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // Отмена регистрации слушателя датчика при приостановке фрагмента
        sensorManager.unregisterListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Создание макета фрагмента и привязка элементов интерфейса
        View rootView = inflater.inflate(R.layout.fragment_accelerometer, container, false);
        stepsCountTextView = rootView.findViewById(R.id.steps_count_text_view);
        return rootView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Обработка изменения данных с датчика шагов
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            float steps = event.values[0];
            updateAccelerometerData(steps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void updateAccelerometerData(float steps) {
        // Обновление интерфейса с данными шагов
        String data = String.format(Locale.getDefault(), "Количество шагов: %.0f", steps);
        stepsCountTextView.setText(data);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, REQUEST_PERMISSION_CODE);
    }
}