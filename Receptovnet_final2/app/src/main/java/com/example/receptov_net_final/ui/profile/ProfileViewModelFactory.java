package com.example.receptov_net_final.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.domain.repository.FavoriteRecipeRepository;
import com.example.domain.repository.UserRepositoryInterface;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    private final UserRepositoryInterface userRepository;
    private final FavoriteRecipeRepository favoriteRecipeRepository;

    public ProfileViewModelFactory(UserRepositoryInterface userRepository, FavoriteRecipeRepository favoriteRecipeRepository) {
        this.userRepository = userRepository;
        this.favoriteRecipeRepository = favoriteRecipeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileViewModel(userRepository, favoriteRecipeRepository);
    }
}

