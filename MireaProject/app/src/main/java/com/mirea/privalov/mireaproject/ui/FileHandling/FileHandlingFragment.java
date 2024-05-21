package com.mirea.privalov.mireaproject.ui.FileHandling;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mirea.privalov.mireaproject.R;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileHandlingFragment extends Fragment {

    private static final int REQUEST_CODE_PICK_FILE = 1001;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_file_handling, container, false);

        root.findViewById(R.id.buttonOpenFilePicker).setOnClickListener(v -> readFileFromExternalStorage());

        return root;
    }

    private void readFileFromExternalStorage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Тип файлов, которые можно выбирать
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Выберите файл"), REQUEST_CODE_PICK_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_PICK_FILE && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri selectedFileUri = data.getData();
                try {
                    List<String> lines = readFileLines(selectedFileUri);
                    Log.w("RawFile", String.format("Read from file successful: %s", lines.toString()));
                    String filePath = createDocx(lines);
                    if (filePath != null) {
                        Log.w("FileLocation", "File saved at: " + filePath);
                    } else {
                        Log.w("FileLocation", "Failed to save file.");
                    }
                } catch (IOException e) {
                    Log.w("RawFile", String.format("Read from file failed: %s", e.getMessage()));
                }
            }
        }
    }

    private List<String> readFileLines(Uri fileUri) throws IOException {
        List<String> lines = new ArrayList<>();
        InputStream inputStream = requireActivity().getContentResolver().openInputStream(fileUri);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    private String createDocx(List<String> lines) {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(path, "my_file.docx");

        try {
            // Создание документа
            XWPFDocument document = new XWPFDocument();
            // Создание параграфа
            XWPFParagraph paragraph = document.createParagraph();
            // Создание строки
            XWPFRun run = paragraph.createRun();

            // Добавление строк из списка в документ
            for (String line : lines) {
                run.setText(line);
                run.addBreak(); // Добавление переноса строки
            }

            // Запись документа в файл
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            document.write(fileOutputStream);
            fileOutputStream.close();

            return file.getAbsolutePath(); // Возвращает путь к созданному файлу

        } catch (IOException e) {
            Log.e("FileCreation", "Error writing file", e);
            return null;
        }
    }
}