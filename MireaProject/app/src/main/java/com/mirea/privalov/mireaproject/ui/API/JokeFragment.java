package com.mirea.privalov.mireaproject.ui.API;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mirea.privalov.mireaproject.Joke;
import com.mirea.privalov.mireaproject.JokeApiClient;
import com.mirea.privalov.mireaproject.JokeService;
import com.mirea.privalov.mireaproject.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JokeFragment extends Fragment {
    private TextView jokeTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, container, false);
        jokeTextView = view.findViewById(R.id.jokeTextView);
        loadRandomJoke();
        return view;
    }

    private void loadRandomJoke() {
        JokeService service = JokeApiClient.getClient().create(JokeService.class);
        Call<Joke> call = service.getRandomJoke();
        call.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                if (response.isSuccessful()) {
                    Joke joke = response.body();
                    if (joke != null) {
                        jokeTextView.setText(joke.getJoke());
                    }
                }
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                jokeTextView.setText("Failed to load joke!");
            }
        });
    }
}