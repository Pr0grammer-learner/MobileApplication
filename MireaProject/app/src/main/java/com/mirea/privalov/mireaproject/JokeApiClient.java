package com.mirea.privalov.mireaproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokeApiClient {
    private static final String BASE_URL = "https://geek-jokes.sameerkumar.website";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
