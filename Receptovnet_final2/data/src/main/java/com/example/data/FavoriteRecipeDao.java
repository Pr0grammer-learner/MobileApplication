package com.example.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.data.model.FavoriteRecipeEntity;

import java.util.List;

@Dao
public interface FavoriteRecipeDao {
    @Insert
    void insert(FavoriteRecipeEntity recipe);

    @Query("SELECT * FROM favorite_recipes")
    List<FavoriteRecipeEntity> getAllFavoriteRecipes(); // Убедитесь, что возвращаемый тип здесь - FavoriteRecipeEntity

    @Query("DELETE FROM favorite_recipes WHERE id = :recipeId")
    void deleteFavoriteRecipe(int recipeId);

    @Query("SELECT * FROM favorite_recipes WHERE name = :name LIMIT 1")
    FavoriteRecipeEntity getFavoriteByName(String name);

    @Query("SELECT * FROM favorite_recipes WHERE id = :id LIMIT 1")
    FavoriteRecipeEntity getFavoriteById(int id);
}

