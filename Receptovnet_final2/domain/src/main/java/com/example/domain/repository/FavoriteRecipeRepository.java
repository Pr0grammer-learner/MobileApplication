package com.example.domain.repository;

import com.example.domain.model.FavoriteRecipe;

import java.util.List;

public interface FavoriteRecipeRepository {
    void addFavoriteRecipe(FavoriteRecipe recipe);
    List<FavoriteRecipe> getAllFavoriteRecipes();
    void deleteFavoriteRecipe(int recipeId);
    boolean isFavorite(int recipeId);
}
