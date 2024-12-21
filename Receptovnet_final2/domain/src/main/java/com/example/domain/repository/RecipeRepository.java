package com.example.domain.repository;

import com.example.domain.model.Meal;

import java.util.List;

public interface RecipeRepository {
    void getMealsAsync(MealCallback callback);

    interface MealCallback {
        void onSuccess(List<Meal> meals);
        void onFailure(Throwable t);
    }
}
