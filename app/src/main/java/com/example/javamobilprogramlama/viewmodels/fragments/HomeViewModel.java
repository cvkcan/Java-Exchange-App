package com.example.javamobilprogramlama.viewmodels.fragments;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.javamobilprogramlama.models.Statics;
import com.example.javamobilprogramlama.models.NewsModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<List<NewsModel>> newsList;
    private final MutableLiveData<Boolean> isLoading;
    private final SharedPreferences sharedPreferences;
    private final String languageCode;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        newsList = new MutableLiveData<>(new ArrayList<>());
        isLoading = new MutableLiveData<>(false);
        sharedPreferences = application.getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        languageCode = Locale.getDefault().getLanguage();
    }

    public LiveData<List<NewsModel>> getNewsList() {
        return newsList;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadNews(String pageNumber) {
        isLoading.setValue(true);
        new FetchNewsTask().execute(pageNumber);
    }

    private class FetchNewsTask extends AsyncTask<String, Void, List<NewsModel>> {
        @Override
        protected List<NewsModel> doInBackground(String... params) {
            List<NewsModel> news = new ArrayList<>();
            String pageNumber = params[0];
            if (languageCode.equals("en")) {
                fetchNewsEN(news, pageNumber);
            } else {
                fetchNewsTR(news, pageNumber);
            }
            return news;
        }

        @Override
        protected void onPostExecute(List<NewsModel> news) {
            newsList.setValue(news);
            isLoading.setValue(false);
        }
    }

    private void fetchNewsEN(List<NewsModel> newsList, String pageNumber) {
        String url = Statics.EnglishNews;
        try {
            Document doc = Jsoup.connect(url)
                    .get();
            Elements generalDoc = doc
                    .select("div#divContentList");
            Elements elementDate = generalDoc
                    .select("dt.date");
            Elements elementUrl = generalDoc
                    .select("dl dd a");

            int startIndex, endIndex;
            int intPageNumber = Integer.parseInt(pageNumber);
            startIndex = (intPageNumber - 1) * 8;
            endIndex = startIndex + 7;
            int count = 0;
            for (int j = startIndex; j < endIndex && j < elementUrl.size() && count < 8; j++) {
                NewsModel news = new NewsModel(elementUrl.get(j).text(),
                        elementDate.get(j).text(),
                        elementUrl.get(j).attr("href"),
                        "en");
                newsList.add(news);
                count++;
            }

            System.out.println(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void fetchNewsTR(List<NewsModel> newsList, String pageNumber) {
        String url = Statics.TurkishNews + pageNumber;
        try {
            Document doc = Jsoup.connect(url).get();

            Elements generalDoc = doc.select("div#content")
                    .select("div.contentLeft").first()
                    .select("div.simpleTable.mBot20").first()
                    .select("div.tBody");

            Elements elementTitle = generalDoc.select("h2.newsTitle1");
            Elements elementUrl = elementTitle.select("a");
            Elements elementDate = generalDoc.select("ul").select("li.cell012.tar.fsn");

            for (int i = 0; i < Math.min(elementTitle.size(), Math.min(elementDate.size(), elementUrl.size())); i++) {
                NewsModel news = new NewsModel(elementTitle.get(i).text(), elementDate.get(i).text(), elementUrl.get(i).attr("href"), "tr");
                newsList.add(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}