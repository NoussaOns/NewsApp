package com.example.android.news.fragments;

import android.os.Bundle;

import com.example.android.news.Constants;
import com.example.android.news.News;
import com.example.android.news.NewsLoader;
import com.example.android.news.NewsUtility;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.Loader;

public class BusinessFragment extends BaseArticlesFragment{

    public BusinessFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        String businessUrl = NewsUtility.getPreferredUrl(getContext(), Constants.JSON_VALUE_BUSINESS);
        return new NewsLoader(getActivity(),businessUrl);
    }
}
