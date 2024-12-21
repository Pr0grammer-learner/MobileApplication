package com.example.receptov_net_final;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.model.FavoriteRecipeEntity;
import com.example.domain.model.FavoriteRecipe;
import com.example.receptov_net_final.ui.home.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<FavoriteRecipeEntity> favoriteRecipes;
    private HomeViewModel recipeViewModel;
    private static final String TAG = "RecipeAdapter";

    public RecipeAdapter(List<FavoriteRecipeEntity> favoriteRecipes, HomeViewModel recipeViewModel) {
        this.favoriteRecipes = favoriteRecipes;
        this.recipeViewModel = recipeViewModel;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        FavoriteRecipeEntity recipeEntity = favoriteRecipes.get(position);
        holder.recipeName.setText(recipeEntity.getName());
        holder.recipeInfo.setText("Category: " + recipeEntity.getCategory());
        holder.recipeDescription.setText(recipeEntity.getInstructions());
        Picasso.get().load(recipeEntity.getImageUrl()).into(holder.recipeImage);

        // Наблюдаем за изменением состояния "Избранное"
        recipeViewModel.isFavorite(recipeEntity).observe((LifecycleOwner) holder.itemView.getContext(), isFavorite -> {
            if (isFavorite) {
                holder.favoriteButton.setBackgroundResource(R.color.color_favorite);
                holder.favoriteButton.setImageResource(R.drawable.ic_favorite_filled); // Изображение для избранного
                Log.d(TAG, "Recipe is favorite: " + recipeEntity.getName());
            } else {
                holder.favoriteButton.setBackgroundResource(R.color.color_default);
                holder.favoriteButton.setImageResource(R.drawable.ic_favorite); // Изображение для не избранного
                Log.d(TAG, "Recipe is not favorite: " + recipeEntity.getName());
            }
        });

        // Логика нажатия кнопки
        holder.favoriteButton.setOnClickListener(v -> {
            Log.d(TAG, "Favorite button clicked for recipe: " + recipeEntity.getName());
            recipeViewModel.isFavorite(recipeEntity).observe((LifecycleOwner) holder.itemView.getContext(), isFavorite -> {
                if (isFavorite) {
                    Log.d(TAG, "Removing recipe from favorites: " + recipeEntity.getName());
                    recipeViewModel.deleteFavoriteRecipe(recipeEntity.getId());
                } else {
                    Log.d(TAG, "Adding recipe to favorites: " + recipeEntity.getName());
                    FavoriteRecipe favoriteRecipe = new FavoriteRecipe(
                            recipeEntity.getName(),
                            recipeEntity.getCategory(),
                            recipeEntity.getInstructions(),
                            recipeEntity.getImageUrl()
                    );
                    recipeViewModel.addFavoriteRecipe(favoriteRecipe);
                }
            });
        });
    }


    @Override
    public int getItemCount() {
        return favoriteRecipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImage;
        TextView recipeName;
        TextView recipeInfo;
        TextView recipeDescription;
        ImageButton favoriteButton;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeInfo = itemView.findViewById(R.id.recipe_info);
            recipeDescription = itemView.findViewById(R.id.recipe_description);
            favoriteButton = itemView.findViewById(R.id.favorite_button);
        }
    }
}

