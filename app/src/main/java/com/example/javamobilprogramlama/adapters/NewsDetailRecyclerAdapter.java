package com.example.javamobilprogramlama.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javamobilprogramlama.R;
import com.example.javamobilprogramlama.databinding.RecyclerGetnewsdetailBinding;
import com.example.javamobilprogramlama.models.NewsDetail;

import java.util.List;

public class NewsDetailRecyclerAdapter extends RecyclerView.Adapter<NewsDetailRecyclerAdapter.NewsDetailVH> {
    private RecyclerGetnewsdetailBinding binding;
    private final List<NewsDetail> newsDetails;

    public NewsDetailRecyclerAdapter(List<NewsDetail> newsDetails) {
        this.newsDetails = newsDetails;
    }

    public static class NewsDetailVH extends RecyclerView.ViewHolder {
        private final RecyclerGetnewsdetailBinding binding;

        public NewsDetailVH(RecyclerGetnewsdetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NewsDetail newsDetail) {
            binding.setNewsDetail(newsDetail);
            binding.executePendingBindings();
        }
    }

    @Override
    public NewsDetailVH onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.recycler_getnewsdetail,
                parent,
                false
        );
        return new NewsDetailVH(binding);
    }

    @Override
    public void onBindViewHolder(NewsDetailVH holder, int position) {
        holder.bind(newsDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return newsDetails.size();
    }
}