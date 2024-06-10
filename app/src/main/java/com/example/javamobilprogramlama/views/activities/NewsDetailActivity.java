package com.example.javamobilprogramlama.views.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.javamobilprogramlama.InternetConnectionChecker;
import com.example.javamobilprogramlama.adapters.NewsDetailRecyclerAdapter;
import com.example.javamobilprogramlama.databinding.ActivityNewsDetailBinding;
import com.example.javamobilprogramlama.viewmodels.activities.NewsDetailViewModel;

public class NewsDetailActivity extends AppCompatActivity {
    private ActivityNewsDetailBinding binding;
    private NewsDetailViewModel viewModel;
    private NewsDetailRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(NewsDetailViewModel.class);

        InternetConnectionChecker.initialize(binding.getRoot(), this, null);
        if (!InternetConnectionChecker.checkInternetConnection()) {
            return;
        }

        String language = getIntent().getStringExtra("language");
        String elementTitle = getIntent().getStringExtra("elementTitle");
        if (elementTitle != null && language != null) {
            viewModel.setPageDetails(elementTitle, language);
        }

        viewModel.getNewsDetails().observe(this, newsDetails -> {
            adapter = new NewsDetailRecyclerAdapter(newsDetails);
            binding.recyclerView2.setLayoutManager(new LinearLayoutManager(NewsDetailActivity.this));
            binding.recyclerView2.setAdapter(adapter);
        });

        System.out.println(language);
    }
}