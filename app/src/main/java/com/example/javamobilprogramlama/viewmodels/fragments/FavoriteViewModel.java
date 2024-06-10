package com.example.javamobilprogramlama.viewmodels.fragments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.javamobilprogramlama.roomdb.AppDatabase;
import com.example.javamobilprogramlama.roomdb.Favorite;

import java.util.List;
import java.util.concurrent.Executors;

public class FavoriteViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Favorite>> favoriteArticles;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        favoriteArticles = new MutableLiveData<>();
    }

    public LiveData<List<Favorite>> getFavoriteArticles() {
        return favoriteArticles;
    }

    public void loadFavoriteArticles() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Favorite> articles = AppDatabase.getInstance(getApplication()).favoriteArticleDao().getAllArticles();
            favoriteArticles.postValue(articles);
        });
    }
}