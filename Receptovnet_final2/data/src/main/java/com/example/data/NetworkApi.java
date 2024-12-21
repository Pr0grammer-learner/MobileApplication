package com.example.data;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import com.example.data.model.MealResponse;

public class NetworkApi {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

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

    public interface MealService {
        @GET("search.php?s=Potato")
        Call<MealResponse> getMeals();
    }
}
