package com.mirea.privalov.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mirea.privalov.myapplication.databinding.ActivityMainBinding;
import com.mirea.privalov.myapplication.databinding.ActivityMusicPlayerBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMusicPlayerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}