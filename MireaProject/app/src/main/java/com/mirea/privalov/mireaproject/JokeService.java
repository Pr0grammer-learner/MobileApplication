package com.mirea.privalov.mireaproject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JokeService {
    @GET("/api?format=json")
    Call<Joke> getRandomJoke();
}
