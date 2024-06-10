package com.example.javamobilprogramlama.viewmodels.activities;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Locale;

public class HomepageViewModel extends AndroidViewModel {

    private final MutableLiveData<String> language = new MutableLiveData<>();
    private final MutableLiveData<Boolean> darkMode = new MutableLiveData<>();

    public HomepageViewModel(@NonNull Application application) {
        super(application);
        SharedPreferences sharedPreferences = application.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String savedLanguage = sharedPreferences.getString("language", null);
        if (savedLanguage != null) {
            language.setValue(savedLanguage);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                LocaleList localeList = application.getResources().getConfiguration().getLocales();
                if (!localeList.isEmpty()) {
                    language.setValue(localeList.get(0).getLanguage());
                }
            } else {
                language.setValue(Locale.getDefault().getLanguage());
            }
        }
        boolean savedDarkMode = sharedPreferences.getBoolean("dark_mode", false);
        darkMode.setValue(savedDarkMode);
        if (!sharedPreferences.contains("dark_mode")) {
            int currentNightMode = application.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_YES:
                    darkMode.setValue(true);
                    break;
                default:
                    darkMode.setValue(false);
                    break;
            }
        }
    }


    public LiveData<String> getLanguage() {
        return language;
    }

    public LiveData<Boolean> getDarkMode() {
        return darkMode;
    }

    public void setLanguage(String languageCode) {
        language.setValue(languageCode);
        saveLanguage(languageCode);
    }

    public void setDarkMode(boolean isDarkMode) {
        darkMode.setValue(isDarkMode);
        saveDarkMode(isDarkMode);
    }

    private void saveLanguage(String languageCode) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", languageCode);
        editor.apply();
    }

    private void saveDarkMode(boolean isDarkMode) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("dark_mode", isDarkMode);
        editor.apply();
    }
}
