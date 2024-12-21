package com.example.receptov_net_final.ui.favoriterecipe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.AppDatabase;
import com.example.data.FavoriteRecipeDao;
import com.example.data.FavoriteRecipeRepositoryImpl;
import com.example.data.model.FavoriteRecipeEntity;
import com.example.domain.repository.FavoriteRecipeRepository;
import com.example.receptov_net_final.R;
import com.example.receptov_net_final.RecipeAdapter;
import com.example.receptov_net_final.ui.home.HomeViewModel;

import java.util.List;

public class FavoriteRecipesFragment extends Fragment {

    private RecyclerView favoriteRecipeRecyclerView;
    private RecipeAdapter recipeAdapter;
    private FavoriteRecipeDao favoriteRecipeDao;
    private FavoriteRecipeRepository favoriteRecipeRepository;
    private HomeViewModel homeViewModel;
    private ImageButton menuButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Создаем разметку для фрагмента
        View view = inflater.inflate(R.layout.fragment_favorite_recipes, container, false);

        // Инициализация RecyclerView
        favoriteRecipeRecyclerView = view.findViewById(R.id.favoriteRecipeRecyclerView);
        favoriteRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Инициализация DAO и репозитория
        favoriteRecipeDao = AppDatabase.getInstance(requireContext()).favoriteRecipeDao();
        favoriteRecipeRepository = new FavoriteRecipeRepositoryImpl(requireContext());

        // Инициализация ViewModel
        homeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.Factory() {
                    @NonNull
                    @Override
                    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                        return (T) new HomeViewModel(favoriteRecipeRepository);
                    }
                }).get(HomeViewModel.class);

        // Загрузка избранных рецептов
        loadFavoriteRecipes();

        return view;
    }

    private void loadFavoriteRecipes() {
        new Thread(() -> {
            List<FavoriteRecipeEntity> favoriteRecipes = favoriteRecipeDao.getAllFavoriteRecipes();
            requireActivity().runOnUiThread(() -> {
                recipeAdapter = new RecipeAdapter(favoriteRecipes, homeViewModel); // Передаем ViewModel
                favoriteRecipeRecyclerView.setAdapter(recipeAdapter);
            });
        }).start();
    }

    // Метод для создания нового экземпляра фрагмента
    public static FavoriteRecipesFragment newInstance() {
        return new FavoriteRecipesFragment();
    }
}