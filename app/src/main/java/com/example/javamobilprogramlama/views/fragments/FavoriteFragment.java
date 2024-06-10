package com.example.javamobilprogramlama.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javamobilprogramlama.adapters.FavoriteRecyclerAdapter;
import com.example.javamobilprogramlama.databinding.FragmentFavoriteBinding;
import com.example.javamobilprogramlama.viewmodels.fragments.FavoriteViewModel;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavoriteViewModel viewModel;
    private FavoriteRecyclerAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        RecyclerView recyclerView = binding.recyclerFavorite;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getFavoriteArticles().observe(getViewLifecycleOwner(), favoriteArticles -> {
            if (favoriteArticles != null) {
                adapter = new FavoriteRecyclerAdapter(favoriteArticles, requireContext(), this::onFavoriteRemoved);
                recyclerView.setAdapter(adapter);
            }
        });

        viewModel.loadFavoriteArticles();
    }

    private void onFavoriteRemoved() {
        getActivity().runOnUiThread(() -> viewModel.loadFavoriteArticles());
    }
}