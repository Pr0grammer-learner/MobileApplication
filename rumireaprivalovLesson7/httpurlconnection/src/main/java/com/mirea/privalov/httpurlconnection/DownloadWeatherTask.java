package com.mirea.privalov.httpurlconnection;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWeatherTask extends AsyncTask<String, Void, String> {
    private TextView textViewWeather;

    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urls[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            result = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject responseJson = new JSONObject(result);
            JSONObject currentWeather = responseJson.getJSONObject("current_weather");
            double temperature = currentWeather.getDouble("temperature");

            // Отображение температуры на экране
            textViewWeather.setText("Температура: " + temperature + "°C");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public DownloadWeatherTask(TextView textViewWeather) {
        this.textViewWeather = textViewWeather;
    }
}
