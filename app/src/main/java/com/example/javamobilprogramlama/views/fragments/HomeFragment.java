package com.example.javamobilprogramlama.views.fragments;

import android.content.Intent;
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

import com.example.javamobilprogramlama.InternetConnectionChecker;
import com.example.javamobilprogramlama.R;
import com.example.javamobilprogramlama.adapters.HomeFragmentRecyclerAdapter;
import com.example.javamobilprogramlama.databinding.FragmentHomeBinding;
import com.example.javamobilprogramlama.models.NewsModel;
import com.example.javamobilprogramlama.viewmodels.fragments.HomeViewModel;
import com.example.javamobilprogramlama.views.activities.NewsDetailActivity;

public class HomeFragment extends Fragment implements HomeFragmentRecyclerAdapter.Listener {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private HomeFragmentRecyclerAdapter adapter;
    private String pageNumber = "1";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        InternetConnectionChecker.initialize(view, getActivity(), this);
        if (!InternetConnectionChecker.checkInternetConnection()) {
            return;
        }

        RecyclerView recyclerView = binding.getHomeRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.bottomNavigationView2.setOnItemSelectedListener(item -> {
            binding.linearProgressIndicator.setVisibility(View.VISIBLE);
            binding.linearProgressIndicator.setIndeterminate(true);
            int itemId = item.getItemId();
            if (itemId == R.id.pageOne) {
                pageNumber = "1";
            } else if (itemId == R.id.pageTwo) {
                pageNumber = "2";
            } else if (itemId == R.id.pageThree) {
                pageNumber = "3";
            } else if (itemId == R.id.pageFour) {
                pageNumber = "4";
            } else if (itemId == R.id.pageFive) {
                pageNumber = "5";
            }
            viewModel.loadNews(pageNumber);
            return true;
        });

        observeViewModel();
        viewModel.loadNews(pageNumber);
    }

    private void observeViewModel() {
        viewModel.getNewsList().observe(getViewLifecycleOwner(), newsList -> {
            if (newsList != null) {
                adapter = new HomeFragmentRecyclerAdapter(newsList, requireContext());
                binding.getHomeRecyclerView.setAdapter(adapter);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                binding.linearProgressIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onItemClick(NewsModel newsResponse) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        intent.putExtra("elementTitle", newsResponse.getUrl());
        intent.putExtra("language", newsResponse.getLanguage());
        startActivity(intent);
    }
}