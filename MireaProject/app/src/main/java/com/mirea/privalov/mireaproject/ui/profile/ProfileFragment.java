package com.mirea.privalov.mireaproject.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.privalov.mireaproject.R;

public class ProfileFragment extends Fragment {

    private EditText editTextName, editTextAge, editTextHeight, editTextWeight;

    public static final String PREFS_NAME = "ProfilePrefs";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        editTextName = root.findViewById(R.id.editTextName);
        editTextAge = root.findViewById(R.id.editTextAge);
        editTextHeight = root.findViewById(R.id.editTextHeight);
        editTextWeight = root.findViewById(R.id.editTextWeight);
        Button buttonSave = root.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileData();
            }
        });

        loadProfileData(); // Загружаем данные при создании фрагмента

        return root;
    }

    private void saveProfileData() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", editTextName.getText().toString());
        editor.putString("age", editTextAge.getText().toString());
        editor.putString("height", editTextHeight.getText().toString());
        editor.putString("weight", editTextWeight.getText().toString());
        editor.apply();
    }

    private void loadProfileData() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editTextName.setText(sharedPreferences.getString("name", ""));
        editTextAge.setText(sharedPreferences.getString("age", ""));
        editTextHeight.setText(sharedPreferences.getString("height", ""));
        editTextWeight.setText(sharedPreferences.getString("weight", ""));
    }
}