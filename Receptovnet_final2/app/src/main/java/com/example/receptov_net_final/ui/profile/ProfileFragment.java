package com.example.receptov_net_final.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.FavoriteRecipeRepositoryImpl;
import com.example.data.UserRepository;
import com.example.domain.repository.FavoriteRecipeRepository;
import com.example.domain.repository.UserRepositoryInterface;
import com.example.receptov_net_final.R;
import com.example.receptov_net_final.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private TextView emailTextView;
    private TextView usernameTextView;
    private TextView favoriteCountTextView;
    private ProfileViewModel profileViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализация репозиториев
        UserRepositoryInterface userRepository = new UserRepository(getContext());
        FavoriteRecipeRepository favoriteRecipeRepository = new FavoriteRecipeRepositoryImpl(getContext());

        // Инициализация ViewModel
        profileViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(userRepository, favoriteRecipeRepository))
                .get(ProfileViewModel.class);

        // Инициализация View
        emailTextView = view.findViewById(R.id.email_text);
        usernameTextView = view.findViewById(R.id.username_text);
        favoriteCountTextView = view.findViewById(R.id.favorite_recipes_text);

        // Наблюдаем за изменениями данных пользователя
        profileViewModel.getUserEmail().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String email) {
                emailTextView.setText(email);
            }
        });

        profileViewModel.getUsername().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String username) {
                usernameTextView.setText(username);
            }
        });

        profileViewModel.getFavoriteCount().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer count) {
                favoriteCountTextView.setText("Favorites: " + count);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        profileViewModel.loadFavoriteCount();
    }
}
