package com.example.javamobilprogramlama.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("SELECT * FROM favorite WHERE url = :url LIMIT 1")
    Favorite getArticleByUrl(String url);

    @Query("SELECT * FROM favorite")
    List<Favorite> getAllArticles();
}