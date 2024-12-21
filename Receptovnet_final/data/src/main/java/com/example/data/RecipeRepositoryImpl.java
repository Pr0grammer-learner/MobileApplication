package com.example.data;

import com.example.data.model.MealResponse;
import com.example.domain.model.Meal;
import com.example.domain.repository.RecipeRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepositoryImpl implements RecipeRepository {

    @Override
    public void getMealsAsync(MealCallback callback) {
        // Асинхронный вызов через Retrofit
        NetworkApi.MealService service = NetworkApi.getClient().create(NetworkApi.MealService.class);
        Call<MealResponse> call = service.getMeals(); // Ensure this matches the correct MealResponse import

        call.enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Преобразование данных из data модели в domain модель
                    List<Meal> domainMeals = new ArrayList<>();
                    for (MealResponse.Meal mealResponse : response.body().getMeals()) {
                        domainMeals.add(mapToDomainMeal(mealResponse));
                    }
                    // Передача преобразованных данных в callback
                    callback.onSuccess(domainMeals);
                } else {
                    callback.onFailure(new Exception("Response unsuccessful"));
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                // Ошибка при вызове API
                callback.onFailure(t);
            }
        });
    }

    // Метод для маппинга из MealResponse.Meal в Meal (доменную модель)
    private Meal mapToDomainMeal(MealResponse.Meal mealResponse) {
        return new Meal(
                mealResponse.getStrMeal(),        // Название блюда
                mealResponse.getStrCategory(),    // Категория
                mealResponse.getStrInstructions(),// Инструкции
                mealResponse.getStrMealThumb()    // URL изображения
        );
    }
}
