package com.example.javamobilprogramlama.views.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.javamobilprogramlama.R;
import com.example.javamobilprogramlama.databinding.ActivityHomepageBinding;
import com.example.javamobilprogramlama.views.fragments.CurrenciesFragment;
import com.example.javamobilprogramlama.views.fragments.HomeFragment;
import com.example.javamobilprogramlama.views.fragments.SettingsFragment;

import java.util.Locale;

public class HomepageActivity extends AppCompatActivity {

    private ActivityHomepageBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("dark_mode",
                getResources().getConfiguration().uiMode == Configuration.UI_MODE_NIGHT_YES);
        updateAppTheme(isDarkMode);
        String languageCode = sharedPreferences.getString("language", Locale.getDefault().getLanguage());
        updateLocale(languageCode);
        replaceFragment(new HomeFragment(), "null");

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home)
                replaceFragment(new HomeFragment(), "null");
            else if (itemId == R.id.settings)
                replaceFragment(new SettingsFragment(), "null");
            else if (itemId == R.id.usd)
                replaceFragment(new CurrenciesFragment(), "USD");
            else if (itemId == R.id.sterlin)
                replaceFragment(new CurrenciesFragment(), "GBP");
            else if (itemId == R.id.euro)
                replaceFragment(new CurrenciesFragment(), "EUR");
            return true;
        });
    }

    private void replaceFragment(Fragment fragment, String type) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("key", type);
        fragment.setArguments(bundle);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }

    private void updateLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }


    private void updateAppTheme(boolean isDarkMode) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}
