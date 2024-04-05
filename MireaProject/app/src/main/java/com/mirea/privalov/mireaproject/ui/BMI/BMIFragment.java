package com.mirea.privalov.mireaproject.ui.BMI;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.privalov.mireaproject.BMIService;
import com.mirea.privalov.mireaproject.R;

public class BMIFragment extends Fragment {

    private EditText heightEditText, weightEditText;
    private TextView resultTextView;
    private Button calculateButton;

    private BMIService bmiService;
    private boolean bound = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BMIService.LocalBinder binder = (BMIService.LocalBinder) service;
            bmiService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_b_m_i, container, false);

        heightEditText = rootView.findViewById(R.id.editTextHeight);
        weightEditText = rootView.findViewById(R.id.editTextWeight);
        resultTextView = rootView.findViewById(R.id.textViewResult);
        calculateButton = rootView.findViewById(R.id.buttonCalculate);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        return rootView;
    }

    private void calculateBMI() {
        if (bound) {
            // Получаем значения роста и веса из EditText
            double height = Double.parseDouble(heightEditText.getText().toString());
            double weight = Double.parseDouble(weightEditText.getText().toString());

            // Выводим значения роста и веса в логи
            Log.d("BMIFragment", "Height: " + height + ", Weight: " + weight);

            // Передаем значения в метод calculateBMI вашего сервиса
            double bmi = bmiService.calculateBMI(height, weight);
            resultTextView.setText(String.format("Твой ИМТ: %.2f", bmi));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), BMIService.class);
        getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bound) {
            getActivity().unbindService(serviceConnection);
            bound = false;
        }
    }
}