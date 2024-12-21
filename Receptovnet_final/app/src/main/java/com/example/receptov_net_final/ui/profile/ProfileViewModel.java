package com.example.receptov_net_final.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.model.FavoriteRecipe;
import com.example.domain.repository.FavoriteRecipeRepository;
import com.example.domain.repository.UserRepositoryInterface;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> userEmail = new MutableLiveData<>();
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<Integer> favoriteCount = new MutableLiveData<>();

    private final UserRepositoryInterface userRepository;
    private final FavoriteRecipeRepository favoriteRecipeRepository;

    public ProfileViewModel(UserRepositoryInterface userRepository, FavoriteRecipeRepository favoriteRecipeRepository) {
        this.userRepository = userRepository;
        this.favoriteRecipeRepository = favoriteRecipeRepository;

        // Инициализация данных
        loadUserData();
        loadFavoriteCount();
    }

    private void loadUserData() {
        String email = userRepository.getEmail();
        String name = userRepository.getUsername();

        userEmail.setValue(email);
        username.setValue(name);
    }

    public void loadFavoriteCount() {
        new Thread(() -> {
            List<FavoriteRecipe> favoriteRecipes = favoriteRecipeRepository.getAllFavoriteRecipes();
            favoriteCount.postValue(favoriteRecipes.size());
        }).start();
    }

    public LiveData<String> getUserEmail() {
        return userEmail;
    }

    public LiveData<String> getUsername() {
        return username;
    }

    public LiveData<Integer> getFavoriteCount() {
        return favoriteCount;
    }
}
