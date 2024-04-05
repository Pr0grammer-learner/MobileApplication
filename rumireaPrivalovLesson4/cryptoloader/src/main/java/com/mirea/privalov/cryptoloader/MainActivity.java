package com.mirea.privalov.cryptoloader;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    public final String TAG = this.getClass().getSimpleName();
    private final int LoaderID = 1234;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.ARG_WORD);
    }

    public void onClickButton(View view) {
        String inputText = editText.getText().toString().trim(); // Получаем текст из EditText
        if (!inputText.isEmpty()) {
            Bundle bundle = new Bundle();
            SecretKey key = MyLoader.generateKey();
            byte[] encryptedMessage = MyLoader.encryptMsg(inputText, key);
            // В вашем методе onClickButton():
            String encryptedMessageString = Base64.encodeToString(encryptedMessage, Base64.DEFAULT);
            bundle.putString(MyLoader.ARG_WORD, encryptedMessageString);
            bundle.putByteArray("key", key.getEncoded());
            LoaderManager.getInstance(this).initLoader(LoaderID, bundle, this);
        } else {
            Toast.makeText(this, "Please enter text to encrypt", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset");
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        if (i == LoaderID) {
            Toast.makeText(this, "onCreateLoader:" + i, Toast.LENGTH_SHORT).show();
            return new MyLoader(this, bundle);
        }
        throw new InvalidParameterException("Invalid loader id");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        if (loader.getId() == LoaderID) {
            try {
                byte[] encryptedMessage = s.getBytes();
                MyLoader myLoader = (MyLoader) loader;
                Bundle bundle = myLoader.getArgs(); // Получаем Bundle из MyLoader
                byte[] keyBytes = bundle.getByteArray("key");
                SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
                byte[] decryptedBytes = MyLoader.decryptMsg(encryptedMessage, key);
                String decryptedMessage = new String(decryptedBytes); // Преобразуем массив байтов в строку
                Log.d(TAG, "onLoadFinished: " + decryptedMessage);
                Toast.makeText(this, "Decrypted message: " + decryptedMessage, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}