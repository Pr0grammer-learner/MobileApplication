package com.example.receptov_net_final.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.data.model.FavoriteRecipeEntity;
import com.example.domain.model.FavoriteRecipe;
import com.example.domain.repository.FavoriteRecipeRepository;

import java.util.concurrent.Executors;

public class HomeViewModel extends ViewModel {
    private final FavoriteRecipeRepository favoriteRecipeRepository;
    private static final String TAG = "RecipeViewModel";

    public HomeViewModel(FavoriteRecipeRepository favoriteRecipeRepository) {
        this.favoriteRecipeRepository = favoriteRecipeRepository;
    }

    public LiveData<Boolean> isFavorite(FavoriteRecipeEntity recipeEntity) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            boolean isFavorite = favoriteRecipeRepository.isFavorite(recipeEntity.getId());
            result.postValue(isFavorite);
            Log.d(TAG, "Checking if recipe is favorite: " + recipeEntity.getName() + " - " + isFavorite);
        });
        return result;
    }

    public void deleteFavoriteRecipe(int recipeId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            favoriteRecipeRepository.deleteFavoriteRecipe(recipeId);
            Log.d(TAG, "Recipe deleted from favorites: ID " + recipeId);
        });
    }

    public void addFavoriteRecipe(FavoriteRecipe favoriteRecipe) {
        Executors.newSingleThreadExecutor().execute(() -> {
            favoriteRecipeRepository.addFavoriteRecipe(favoriteRecipe);
            Log.d(TAG, "Recipe added to favorites: " + favoriteRecipe.getName());
        });
    }
}