package com.example.dialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Идет загрузка...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Установка стиля индикатора загрузки (в данном случае используется спиннер)
        return progressDialog;
    }
}
