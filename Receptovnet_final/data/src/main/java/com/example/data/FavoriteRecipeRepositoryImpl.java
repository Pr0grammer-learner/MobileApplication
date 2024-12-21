package com.example.data;

import android.content.Context;

import com.example.data.model.FavoriteRecipeEntity;
import com.example.domain.model.FavoriteRecipe;
import com.example.domain.repository.FavoriteRecipeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class FavoriteRecipeRepositoryImpl implements FavoriteRecipeRepository {
    private final FavoriteRecipeDao favoriteRecipeDao;

    public FavoriteRecipeRepositoryImpl(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        favoriteRecipeDao = db.favoriteRecipeDao();
    }

    @Override
    public void addFavoriteRecipe(FavoriteRecipe recipe) {
        FavoriteRecipeEntity entity = new FavoriteRecipeEntity(
                recipe.getName(),
                recipe.getCategory(),
                recipe.getInstructions(),
                recipe.getImageUrl()
        );
        favoriteRecipeDao.insert(entity);
    }

    @Override
    public List<FavoriteRecipe> getAllFavoriteRecipes() {
        List<FavoriteRecipeEntity> entities = favoriteRecipeDao.getAllFavoriteRecipes();
        return entities.stream().map(entity -> new FavoriteRecipe(
                entity.getName(),
                entity.getCategory(),
                entity.getInstructions(),
                entity.getImageUrl()
        )).collect(Collectors.toList());
    }

    @Override
    public void deleteFavoriteRecipe(int recipeId) {
        favoriteRecipeDao.deleteFavoriteRecipe(recipeId);
    }

    @Override
    public boolean isFavorite(int recipeId) { // Обновленный метод
        // Проверяем, находится ли рецепт в избранном
        return favoriteRecipeDao.getFavoriteById(recipeId) != null; // Предполагая, что у вас есть этот метод
    }
}
