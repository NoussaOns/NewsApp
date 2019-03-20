package com.example.android.news;
import android.content.Context;

import com.example.android.news.News;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    //query url
    private String mUrl;

    /**
     * Construct a new {@link NewsLoader}.
     * @param context of the activity
     * @param url to load data from
     */
    public NewsLoader(@NonNull Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        if(mUrl == null)
             return null;

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
