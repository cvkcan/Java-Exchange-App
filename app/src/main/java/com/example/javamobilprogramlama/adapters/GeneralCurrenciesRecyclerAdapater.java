package com.example.javamobilprogramlama.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javamobilprogramlama.apis.genelpara.ApiResponse;
import com.example.javamobilprogramlama.chart.CreateChart;
import com.example.javamobilprogramlama.databinding.RecyclerGetcurrencyBinding;

import java.util.List;

public class GeneralCurrenciesRecyclerAdapater extends RecyclerView.Adapter<GeneralCurrenciesRecyclerAdapater.CurrenciesVH> {
    private final List<ApiResponse> currency;
    private final String type;

    public GeneralCurrenciesRecyclerAdapater(List<ApiResponse> currency, String type) {
        this.currency = currency;
        this.type = type;
    }

    public static class CurrenciesVH extends RecyclerView.ViewHolder {
        private final RecyclerGetcurrencyBinding binding;

        public CurrenciesVH(RecyclerGetcurrencyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ApiResponse apiResponse, String type, Context context) {
            binding.setApiResponse(apiResponse);
            binding.setType(type);
            binding.executePendingBindings();

            String currencyType = getLocalizedCurrencyType(type, context);
            String currencyBuyingPrice;
            String currencySellingPrice;
            String currencyVariant;

            switch (type) {
                case "USD":
                    currencyBuyingPrice = apiResponse.getUsd().getForexBuying();
                    currencySellingPrice = apiResponse.getUsd().getForexSelling();
                    currencyVariant = apiResponse.getUsd().getVariation();
                    break;
                case "EUR":
                    currencyBuyingPrice = apiResponse.getEuro().getForexBuying();
                    currencySellingPrice = apiResponse.getEuro().getForexSelling();
                    currencyVariant = apiResponse.getEuro().getVariation();
                    break;
                default:
                    currencyBuyingPrice = apiResponse.getSterlin().getForexBuying();
                    currencySellingPrice = apiResponse.getSterlin().getForexSelling();
                    currencyVariant = apiResponse.getSterlin().getVariation();
                    break;
            }

            binding.currencyType.setText(currencyType);
            binding.currencyBuyingPrice.setText(currencyBuyingPrice);
            binding.currencySellingPrice.setText(currencySellingPrice);
            binding.currencyVariant.setText(currencyVariant);
        }

        private String getLocalizedCurrencyType(String type, Context context) {
            Configuration config = context.getResources().getConfiguration();
            if (config.getLocales().get(0).getLanguage().equals("en")) {
                switch (type) {
                    case "USD":
                        return "USD";
                    case "EUR":
                        return "EUR";
                    default:
                        return "GBP";
                }
            } else {
                switch (type) {
                    case "USD":
                        return "DOLAR";
                    case "EUR":
                        return "EURO";
                    default:
                        return "STERLİN";
                }
            }
        }
    }

    @NonNull
    @Override
    public CurrenciesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerGetcurrencyBinding binding = RecyclerGetcurrencyBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);

        var create = new CreateChart(binding, binding.lineChart);
        binding.button1.setOnClickListener(it -> create.fetchData("week", type));
        binding.button2.setOnClickListener(it -> create.fetchData("3month", type));
        binding.button3.setOnClickListener(it -> create.fetchData("6month", type));
        binding.button4.setOnClickListener(it -> create.fetchData("year", type));

        return new CurrenciesVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrenciesVH holder, int position) {
        holder.bind(currency.get(position), type, holder.itemView.getContext());
    }

    @Override
    public int getItemCount() {
        return currency.size();
    }
}