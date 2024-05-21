package com.mirea.privalov.mireaproject.ui.audiorecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mirea.privalov.mireaproject.R;

import java.io.File;
import java.io.IOException;

public class AudioRecordFragment extends Fragment {
    private static final int REQUEST_CODE_PERMISSION = 200;
    private final String TAG = AudioRecordFragment.class.getSimpleName();
    private boolean isWork;
    // Путь к файлу записи
    private String recordFilePath;
    private Button recordButton = null;
    private Button playButton = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    boolean isStartRecording = true;
    boolean isStartPlaying = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_audio_record, container, false);

        recordButton = rootView.findViewById(R.id.recordButton);
        playButton = rootView.findViewById(R.id.playButton);
        playButton.setEnabled(false);

        recordFilePath = new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                "/audiorecordtest.3gp").getAbsolutePath();

        // Проверка разрешений на запись аудио и запись на внешнюю память
        int audioRecordPermissionStatus = ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.RECORD_AUDIO);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            // Выполняется запрос к пользователю на получение необходимых разрешений
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION);
        }

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartRecording) {
                    // Начать запись
                    startRecording();
                    recordButton.setText("Stop recording");
                    playButton.setEnabled(false);
                } else {
                    // Остановить запись
                    stopRecording();
                    recordButton.setText("Start recording");
                    playButton.setEnabled(true);
                }
                // Инвертировать статус записи
                isStartRecording = !isStartRecording;
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStartPlaying) {
                    // Начать воспроизведение
                    startPlaying();
                    playButton.setText("Stop playing");
                    recordButton.setEnabled(false);
                } else {
                    // Остановить воспроизведение
                    stopPlaying();
                    playButton.setText("Start playing");
                    recordButton.setEnabled(true);
                }
                // Инвертировать статус воспроизведения
                isStartPlaying = !isStartPlaying;
            }
        });

        return rootView;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        // производится проверка полученного результата от пользователя на запрос разрешения Camera
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                isWork = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!isWork) requireActivity().finish();
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
        recorder.start();
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
    }
}