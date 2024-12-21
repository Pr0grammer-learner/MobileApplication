package com.example.receptov_net_final.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.FavoriteRecipeRepositoryImpl;
import com.example.data.RecipeRepositoryImpl;
import com.example.data.model.FavoriteRecipeEntity;
import com.example.domain.model.Meal;
import com.example.domain.repository.FavoriteRecipeRepository;
import com.example.domain.repository.RecipeRepository;
import com.example.receptov_net_final.R;
import com.example.receptov_net_final.RecipeAdapter;
import com.example.receptov_net_final.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private RecipeRepository recipeRepository;
    private FavoriteRecipeRepository favoriteRecipeRepository;
    private HomeViewModel recipeViewModel;

    public HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Инициализация репозиториев
        recipeRepository = new RecipeRepositoryImpl();
        favoriteRecipeRepository = new FavoriteRecipeRepositoryImpl(requireContext());

        // Инициализация ViewModel
        recipeViewModel = new ViewModelProvider(this, new HomeViewModelFactory(favoriteRecipeRepository)).get(HomeViewModel.class);

        // Загружаем рецепты
        loadRecipes();

        return view;
    }

    private void loadRecipes() {
        recipeRepository.getMealsAsync(new RecipeRepository.MealCallback() {
            @Override
            public void onSuccess(List<Meal> meals) {
                if (meals != null) {
                    List<FavoriteRecipeEntity> favoriteRecipeEntities = convertToFavoriteRecipeEntities(meals);
                    recipeAdapter = new RecipeAdapter(favoriteRecipeEntities, recipeViewModel);
                    recyclerView.setAdapter(recipeAdapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("RecipesFragment", "Failed to load meals: " + t.getMessage());
            }
        });
    }

    private List<FavoriteRecipeEntity> convertToFavoriteRecipeEntities(List<Meal> meals) {
        List<FavoriteRecipeEntity> favoriteRecipes = new ArrayList<>();
        for (Meal meal : meals) {
            FavoriteRecipeEntity recipeEntity = new FavoriteRecipeEntity(
                    meal.getName(),
                    meal.getCategory(),
                    meal.getInstructions(),
                    meal.getImageUrl()
            );
            favoriteRecipes.add(recipeEntity);
        }
        return favoriteRecipes;
    }
}
