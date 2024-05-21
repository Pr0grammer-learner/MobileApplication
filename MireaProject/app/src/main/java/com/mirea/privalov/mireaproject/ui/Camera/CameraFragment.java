package com.mirea.privalov.mireaproject.ui.Camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.mirea.privalov.mireaproject.R;
import com.mirea.privalov.mireaproject.databinding.FragmentCameraBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class CameraFragment extends Fragment {
    // Объявление переменной TextView
    private TextView nutritionTextView;
    private boolean isWork = false;
    private Uri imageUri;
    private FragmentCameraBinding fragmentCameraBinding;
    private final ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
        isWork = permissions.containsValue(true);
    });
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentCameraBinding = FragmentCameraBinding.inflate(inflater, container, false);
        View root = fragmentCameraBinding.getRoot();
        // Инициализация TextView через привязку
        TextView nutritionTextView = fragmentCameraBinding.nutritionTextView;

        int	cameraPermissionStatus = ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.CAMERA);
        int	storagePermissionStatus = ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if	(cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus
                ==	PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            requestPermissionLauncher.launch(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }

        //	Создание функции обработки результата от системного приложения «камера»
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // Устанавливаем изображение в ImageView
                    fragmentCameraBinding.imageView.setImageURI(imageUri);

                    // Генерируем случайные значения для БЖУ
                    Random random = new Random();
                    int proteins = random.nextInt(901) + 100; // случайное число от 100 до 1000
                    int fats = random.nextInt(901) + 100; // случайное число от 100 до 1000
                    int carbohydrates = random.nextInt(901) + 100; // случайное число от 100 до 1000

                    // Формируем строку с информацией о питательных веществах
                    String nutritionInfo = "БЖУ: Белки - " + proteins + ", жиры - " + fats + ", углеводы - " + carbohydrates;

                    // Устанавливаем сгенерированную информацию в TextView
                    fragmentCameraBinding.nutritionTextView.setText(nutritionInfo);
                }
            }
        };

        ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                callback);

        // Обработчик нажатия на кнопку "takePhotoButton"
        fragmentCameraBinding.takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Создание интента для запуска камеры
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                // Проверка на наличие разрешений для камеры
                if (isWork) {
                    try {
                        // Создание файла для сохранения изображения
                        File photoFile = createImageFile();

                        // Генерирование URI для файла на основе authorities
                        String authorities = requireContext().getPackageName() + ".mireaprovider";
                        imageUri = FileProvider.getUriForFile(requireContext(), authorities, photoFile);

                        // Установка пути для сохранения изображения
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                        // Запуск активности камеры
                        cameraActivityResultLauncher.launch(cameraIntent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Если разрешения не получены, вывод сообщения
                    Toast.makeText(requireContext(), "Нет разрешений", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";

        File storageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }
}