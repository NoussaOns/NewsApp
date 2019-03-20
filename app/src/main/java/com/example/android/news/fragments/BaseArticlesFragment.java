package com.example.android.news.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.news.News;
import com.example.android.news.NewsLoader;
import com.example.android.news.NewsUtility;
import com.example.android.news.R;
import com.example.android.news.adapters.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class BaseArticlesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

    private NewsAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mLoadringIndicator;
    private View mContainer;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private NetworkInfo networkInfo;
    private ConnectivityManager connectivityManager;

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContainer = inflater.inflate(R.layout.fragment_main, container, false);
        mEmptyStateTextView = mContainer.findViewById(R.id.no_connection_tv);
        mLoadringIndicator = mContainer.findViewById(R.id.progress_bar);
        updateUi(new ArrayList<News>());


        //Check state of network
         connectivityManager =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // currently active data network
         networkInfo = connectivityManager.getActiveNetworkInfo();

        initializeLoader(networkInfo != null && networkInfo.isConnected());

        return mContainer;
    }

    /**
     * Initilize loader depending on network connectivity
     * @param isConnected
     */
    private void initializeLoader(boolean isConnected){
        if (isConnected){
            // get a reference to the loader manager
            LoaderManager loaderManager = getLoaderManager();

            //initialize the loader
            loaderManager.initLoader(NEWS_LOADER_ID,null, this);
        } else {
            // display an error if no connection is available
            mLoadringIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(getString(R.string.no_connection_available));
        }
    }

    private void updateUi(final ArrayList<News> news){
        //find views
        mRecyclerView = mContainer.findViewById(R.id.politics_recycler_view);
        mAdapter = new NewsAdapter(getActivity(), news);
        if (news == null){
            // if the recycler view is empty
            mRecyclerView.setVisibility(View.GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);

            Log.e("Base Fragment", "Array list of news is null");
        }
        // if the recycler view is not empty
        else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setVisibility(View.GONE);

            Log.e(getClass().getName(),"news items added to the list successfully");

            //set the recycler view
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            // the swipe refresh toolbar
            mSwipeRefreshLayout = mContainer.findViewById(R.id.swipe_refresh);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initializeLoader(networkInfo != null && networkInfo.isConnected());
                }
            });

        }
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri.Builder uriBuilder = NewsUtility.getPreferredUri(getContext());
        // create a loader for the given url
        return new NewsLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> news) {
        mLoadringIndicator.setVisibility(View.GONE);
        //set the empty text state
        mEmptyStateTextView.setText(getString(R.string.no_news_found));

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            Log.e("n of items in news", "" + news.size());
            updateUi((ArrayList<News>) news);
        }
        // hide the animation after finishing loading
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        //clear existing data
        mAdapter.clearAll();
    }
}
