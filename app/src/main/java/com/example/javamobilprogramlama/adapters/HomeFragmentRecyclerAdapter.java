package com.example.javamobilprogramlama.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.javamobilprogramlama.views.activities.NewsDetailActivity;

import java.util.List;

public class HomeFragmentRecyclerAdapter extends RecyclerView.Adapter<HomeFragmentRecyclerAdapter.HomeVH> {
    private final List<NewsModel> news;
    private final Context context;

    public HomeFragmentRecyclerAdapter(List<NewsModel> news, Context context) {
        this.news = news;
        this.context = context;
    }

    public interface Listener {
        void onItemClick(NewsModel newsResponse);
    }

    static class HomeVH extends RecyclerView.ViewHolder {
        private final RecyclerGethomeBinding binding;
        private final ImageView favoriteIcon;

        HomeVH(RecyclerGethomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.favoriteIcon = binding.getRoot().findViewById(R.id.favorite_icon);
        }

        void bind(NewsModel newsResponse, Context context) {
            binding.setNews(newsResponse);
            binding.executePendingBindings();

            favoriteIcon.setOnClickListener(v -> {
                AppDatabase db = AppDatabase.getInstance(context);
                FavoriteDao favoriteArticleDao = db.favoriteArticleDao();

                new Thread(() -> {
                    Favorite existingArticle = favoriteArticleDao.getArticleByUrl(newsResponse.getUrl());
                    if (existingArticle != null) {
                        favoriteArticleDao.delete(existingArticle);
                        favoriteIcon.setImageResource(R.drawable.baseline_favorite_border);
                    } else {
                        Favorite newFavorite = new Favorite(newsResponse.getUrl(), newsResponse.getTitle(), newsResponse.getDateAndWriter());
                        favoriteArticleDao.insert(newFavorite);
                        favoriteIcon.setImageResource(R.drawable.baseline_favorite);
                    }
                }).start();
            });

            new Thread(() -> {
                AppDatabase db = AppDatabase.getInstance(context);
                FavoriteDao favoriteArticleDao = db.favoriteArticleDao();
                Favorite existingArticle = favoriteArticleDao.getArticleByUrl(newsResponse.getUrl());

                if (existingArticle != null) {
                    favoriteIcon.setImageResource(R.drawable.baseline_favorite);
                } else {
                    favoriteIcon.setImageResource(R.drawable.baseline_favorite_border);
                }
            }).start();
        }
    }

    @NonNull
    @Override
    public HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerGethomeBinding binding = RecyclerGethomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HomeVH(binding);
    }

    @Override
    public void onBindViewHolder(HomeVH holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NewsDetailActivity.class);
            intent.putExtra("elementTitle", news.get(position).getUrl());
            intent.putExtra("language", news.get(position).getLanguage());
            v.getContext().startActivity(intent);
        });
        holder.bind(news.get(position), context);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}