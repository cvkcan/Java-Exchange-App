package com.example.javamobilprogramlama.viewmodels.activities;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.javamobilprogramlama.models.NewsDetail;
import com.example.javamobilprogramlama.models.Statics;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailViewModel extends AndroidViewModel {

    private MutableLiveData<List<NewsDetail>> newsDetails;
    private String pageUrl;
    private String pageLanguage;

    public NewsDetailViewModel(Application application) {
        super(application);
        newsDetails = new MutableLiveData<>();
    }

    public LiveData<List<NewsDetail>> getNewsDetails() {
        return newsDetails;
    }

    public void setPageDetails(String pageUrl, String pageLanguage) {
        this.pageUrl = pageUrl;
        this.pageLanguage = pageLanguage;
        fetchNewsDetails();
    }

    private void fetchNewsDetails() {
        new Thread(() -> {
            List<NewsDetail> newsList = new ArrayList<>();
            if ("tr".equals(pageLanguage)) {
                getNewsDetailTR(newsList);
            } else {
                getNewsDetailEN(newsList);
            }
            newsDetails.postValue(newsList);
        }).start();
    }

    private void getNewsDetailTR(List<NewsDetail> newsList) {
        try {
            String url = Statics.TurkishNewsDetail + pageUrl;
            Document doc = Jsoup.connect(url).userAgent("mozilla").get();
            Elements elementTitles = doc.select("div#bPWhite")
                    .select("div#content")
                    .select("div.contentLeft")
                    .select("h1.news-detail-title.selectionShareable.pageTitle");
            Elements elementContents = doc.select("div#bPWhite")
                    .select("div#content")
                    .select("div.contentLeft")
                    .select("div.news-detail-text.newsDetail.mBot20")
                    .select("div.tag-content")
                    .select("p");
            Elements elementDates = doc.select("div#bPWhite")
                    .select("div#content")
                    .select("div.contentLeft")
                    .select("div.dtSizeLine")
                    .select("span.dateTime");

            StringBuilder totalValue = new StringBuilder();
            for (String currentValue : elementContents.eachText()) {
                totalValue.append(currentValue).append("\n\n");
            }

            for (int i = 0; i < Math.min(elementTitles.size(), Math.min(elementContents.size(), elementDates.size())); i++) {
                NewsDetail news = new NewsDetail(elementTitles.get(i).text(), totalValue.toString(), elementDates.get(i).text());
                newsList.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNewsDetailEN(List<NewsDetail> newsList) {
        try {
            String url = Statics.EnglishNewsDetail + pageUrl;
            System.out.println(pageUrl);
            Document doc = Jsoup.connect(url).userAgent("mozilla").get();
            Elements generalDoc = doc
                    .select("div#news-detail");
            Elements titleElement = generalDoc
                    .select("h1");
            Elements dateElement = generalDoc
                    .select("h6");
            Elements contentElement = generalDoc.select("div#divContentArea");
            StringBuilder totalValue = new StringBuilder();
            int pIndex = 0;
            for (org.jsoup.nodes.Element element : contentElement.select("p")) {
                if (pIndex == 0 || pIndex == 2 || pIndex == 3) {
                    pIndex++;
                    continue;
                }
                totalValue.append(element.text()).append("\n\n");
                pIndex++;
            }
            // 0,2,3
            NewsDetail news = new NewsDetail(titleElement.text(), totalValue.toString(), dateElement.text());
            newsList.add(news);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}