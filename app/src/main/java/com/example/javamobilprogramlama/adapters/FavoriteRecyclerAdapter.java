package com.example.javamobilprogramlama.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javamobilprogramlama.R;
import com.example.javamobilprogramlama.databinding.RecyclerGethomeBinding;
import com.example.javamobilprogramlama.models.NewsModel;
import com.example.javamobilprogramlama.roomdb.AppDatabase;
import com.example.javamobilprogramlama.roomdb.Favorite;
import com.example.javamobilprogramlama.roomdb.FavoriteDao;

import java.util.List;

public class FavoriteRecyclerAdapter extends RecyclerView.Adapter<FavoriteRecyclerAdapter.FavoriteVH> {
    private final List<Favorite> favoriteArticles;
    private final Context context;
    private final OnFavoriteRemovedListener onFavoriteRemovedListener;

    public FavoriteRecyclerAdapter(List<Favorite> favoriteArticles, Context context, OnFavoriteRemovedListener listener) {
        this.favoriteArticles = favoriteArticles;
        this.context = context;
        this.onFavoriteRemovedListener = listener;
    }

    public interface OnFavoriteRemovedListener {
        void onFavoriteRemoved();
    }

    static class FavoriteVH extends RecyclerView.ViewHolder {
        private final RecyclerGethomeBinding binding;
        private final ImageView favoriteIcon;

        FavoriteVH(RecyclerGethomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.favoriteIcon = binding.getRoot().findViewById(R.id.favorite_icon);
        }

        void bind(Favorite article, Context context, OnFavoriteRemovedListener listener) {
            NewsModel newsModel = new NewsModel(article.getTitle(), article.getDate(), article.getUrl(), article.getDate());
            binding.setNews(newsModel);
            binding.executePendingBindings();

            AppDatabase db = AppDatabase.getInstance(context);
            FavoriteDao favoriteArticleDao = db.favoriteArticleDao();

            favoriteIcon.setImageResource(R.drawable.baseline_favorite);

            favoriteIcon.setOnClickListener(v -> {
                new Thread(() -> {
                    favoriteArticleDao.delete(article);
                    listener.onFavoriteRemoved();
                }).start();
            });
        }
    }

    @NonNull
    @Override
    public FavoriteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerGethomeBinding binding = RecyclerGethomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteVH(binding);
    }

    @Override
    public void onBindViewHolder(FavoriteVH holder, int position) {
        holder.bind(favoriteArticles.get(position), context, onFavoriteRemovedListener);
    }

    @Override
    public int getItemCount() {
        return favoriteArticles.size();
    }
}