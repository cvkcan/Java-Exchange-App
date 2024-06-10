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

import com.example.javamobilprogramlama.InternetConnectionChecker;
import com.example.javamobilprogramlama.adapters.GeneralCurrenciesRecyclerAdapater;
import com.example.javamobilprogramlama.databinding.FragmentCurrenciesBinding;
import com.example.javamobilprogramlama.viewmodels.fragments.CurrenciesViewModel;

import java.util.Collections;

public class CurrenciesFragment extends Fragment {

    private FragmentCurrenciesBinding binding;
    private CurrenciesViewModel viewModel;
    private GeneralCurrenciesRecyclerAdapater adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCurrenciesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CurrenciesViewModel.class);
        InternetConnectionChecker.initialize(view, getActivity(), this);
        if (!InternetConnectionChecker.checkInternetConnection()) {
            return;
        }

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        observeViewModel();
        assert getArguments() != null;
        String currencyType = getArguments().getString("key");
        viewModel.loadCurrencyData(currencyType);
    }

    private void observeViewModel() {
        viewModel.getCurrencyModel().observe(getViewLifecycleOwner(), apiResponse -> {
            if (apiResponse != null) {
                assert getArguments() != null;
                adapter = new GeneralCurrenciesRecyclerAdapater(Collections.singletonList(apiResponse),
                        getArguments().getString("key"));
                binding.recyclerView.setAdapter(adapter);
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                binding.linearProgressIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });
    }
}