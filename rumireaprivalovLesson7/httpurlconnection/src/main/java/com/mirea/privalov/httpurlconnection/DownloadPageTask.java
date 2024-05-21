package com.mirea.privalov.httpurlconnection;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadPageTask extends AsyncTask<String, Void, String> {
    private TextView textView;
    private double latitude;
    private double longitude;

    public DownloadPageTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        textView.setText("Загружаем...");
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return downloadIpInfo(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject responseJson = new JSONObject(result);
            Log.d(MainActivity.class.getSimpleName(), "Response: " + responseJson);
            String ip = responseJson.getString("ip");
            Log.d(MainActivity.class.getSimpleName(), "IP: " + ip);

            // Извлечение координат из JSON-ответа
            String loc = responseJson.optString("loc");
            String[] coordinates = loc.split(",");
            String latitude = coordinates[0];
            String longitude = coordinates[1];

            // Формирование URL для запроса погоды
            String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true";

            // Выполнение асинхронной загрузки погоды
            new DownloadWeatherTask(textView).execute(weatherUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String downloadIpInfo(String address) throws IOException {
        InputStream inputStream = null;
        String data = "";
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(100000);
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read;
                byte[] buffer = new byte[1024];
                while ((read = inputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, read);
                }
                bos.close();
                data = bos.toString();
            } else {
                data = connection.getResponseMessage() + ". Error Code: " + responseCode;
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return data;
    }
}