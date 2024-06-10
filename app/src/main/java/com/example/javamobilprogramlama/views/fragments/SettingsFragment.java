package com.example.javamobilprogramlama.views.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.example.javamobilprogramlama.R;
import com.example.javamobilprogramlama.databinding.FragmentSettingsBinding;
import com.example.javamobilprogramlama.views.activities.LauncherActivity;

import java.util.Locale;


public class SettingsFragment extends Fragment {

    private SwitchCompat switchDarkMode;
    private SwitchCompat switchLanguage;
    private FragmentSettingsBinding binding;
    private SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater,
                container,
                false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchDarkMode = binding.switchDarkMode;
        switchLanguage = binding.switchLanguage;
        switchDarkMode.setChecked(sharedPreferences.getBoolean("dark_mode",getResources().getConfiguration().uiMode == Configuration.UI_MODE_NIGHT_YES ));
        switchLanguage.setChecked(sharedPreferences.getString("language", Locale.getDefault().getLanguage()).equals("en"));

        switchLanguage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("language", isChecked ? "en" : "tr");
                editor.apply();
                updateLocale(isChecked ? "en" : "tr");
            }
        });

        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("dark_mode", isChecked);
                editor.apply();
                updateAppTheme(isChecked);
            }
        });

        binding.favoriteButton.setOnClickListener(v -> {
            replaceFragment(new FavoriteFragment(), "favorite");
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void updateAppTheme(boolean isDarkMode) {
        int mode = isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(mode);
        refreshApp();
    }

    private void updateLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        refreshApp();
    }

    private void refreshApp() {
        Intent intent = new Intent(requireContext(), LauncherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void replaceFragment(Fragment fragment, String type) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("key", type);
            fragment.setArguments(bundle);
            transaction.replace(R.id.frameLayout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}