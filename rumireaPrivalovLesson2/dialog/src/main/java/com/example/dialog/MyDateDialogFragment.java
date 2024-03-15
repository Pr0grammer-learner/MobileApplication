package com.example.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class MyDateDialogFragment extends DialogFragment {
    private String date = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this::onDateSet, year, month, dayOfMonth);
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (month < 10){
            date = dayOfMonth + "/" + "0" + (month + 1) + "/" + year;
        }
        else {
            date = dayOfMonth + "/" +  (month + 1) + "/" + year;
        }
        View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, "Вы выбрали: " + date, Snackbar.LENGTH_LONG).show();
    }
}
