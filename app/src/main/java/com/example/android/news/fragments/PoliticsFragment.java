package com.example.android.news.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.news.Constants;
import com.example.android.news.News;
import com.example.android.news.NewsLoader;
import com.example.android.news.NewsUtility;
import com.example.android.news.R;
import com.example.android.news.adapters.NewsAdapter;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoliticsFragment extends BaseArticlesFragment {

    public PoliticsFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        String politicsUrl = NewsUtility.getPreferredUrl(getContext(), Constants.JSON_VALUE_POLITICS);
        return new NewsLoader(getActivity(),politicsUrl);
    }
}
