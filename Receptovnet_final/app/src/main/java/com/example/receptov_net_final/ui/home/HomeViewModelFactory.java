package com.example.receptov_net_final.ui.home;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.domain.repository.FavoriteRecipeRepository;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private final FavoriteRecipeRepository favoriteRecipeRepository;

    public HomeViewModelFactory(FavoriteRecipeRepository favoriteRecipeRepository) {
        this.favoriteRecipeRepository = favoriteRecipeRepository;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(favoriteRecipeRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
