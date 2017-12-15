package com.apps.anesabml.latest;


import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>>,
        SwipeRefreshLayout.OnRefreshListener {

    /**
     * URLs to query the news
     */
    private static final String[] REQUEST_URLS =
            {"https://content.guardianapis.com/search?show-fields=thumbnail,shortUrl&page-size=20&api-key=test",
                    "https://content.guardianapis.com/world?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.com/sport?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.com/football?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.com/commentisfree?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.com/culture?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.com/business?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.com/lifeandstyle?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.com/fashion?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.com/environment?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.com/science?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.com/technology?show-fields=thumbnail,shortUrl&api-key=test",
                    "https://content.guardianapis.,com/travel?show-fields=thumbnail,shortUrl&api-key=test"};

    /**
     * Id of the loader
     */
    private static final int LOADER_ID = 1;

    /**
     * Title of the activity
     */
    private String mTitle;

    private Activity activity;
    private NewsAdapter mAdapter;
    private LinearLayout mEmptyStateLayout;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mRefreshLayout;
    private LoaderManager loaderManager;
    private ConnectivityManager connMgr;
    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = getActivity();

        // Get a reference to the LoaderManager
        loaderManager = getLoaderManager();

        // Get the title of the activity
        mTitle = activity.getTitle().toString();

        // Find the list view in the layout
        ListView listView = (ListView) activity.findViewById(R.id.list);

        // Find the empty text view in the layout.
        mEmptyStateLayout = (LinearLayout) activity.findViewById(R.id.empty_state);

        // Hide the empty state
        mEmptyStateLayout.setVisibility(View.GONE);

        // Find the progress bar in tha layout
        mProgressBar = (ProgressBar) activity.findViewById(R.id.progress_bar);

        // Find the swipe refresh layout
        mRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.swipe_refresh);
        // Set on refresh listener
        mRefreshLayout.setOnRefreshListener(this);

        // Initialize the adapter with the activity and an empty list
        mAdapter = new NewsAdapter(activity, new ArrayList<News>());

        // Set the adapter to the list
        listView.setAdapter(mAdapter);

        // Get a reference to the ConnectivityManager to check
        // state of network connectivity
        connMgr = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Initialize the loader
            loaderManager.initLoader(LOADER_ID, null, this);

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            mProgressBar.setVisibility(View.GONE);

            // Show the empty state
            mEmptyStateLayout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_list, container, false);

        return rootView;
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        if (mTitle.equals("Latest")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[0]);
        } else if (mTitle.equals("World")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[1]);
        } else if (mTitle.equals("Sport")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[2]);
        } else if (mTitle.equals("Football")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[3]);
        } else if (mTitle.equals("Opinion")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[4]);
        } else if (mTitle.equals("Culture")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[5]);
        } else if (mTitle.equals("Business")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[6]);
        } else if (mTitle.equals("Lifestyle")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[7]);
        } else if (mTitle.equals("Fashion")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[8]);
        } else if (mTitle.equals("Environment")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[9]);
        } else if (mTitle.equals("Science")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[10]);
        } else if (mTitle.equals("Tech")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[11]);
        } else if (mTitle.equals("Travel")) {
            return new NewsLoader(getActivity(), REQUEST_URLS[12]);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // Hide the progress bar
        mProgressBar.setVisibility(View.GONE);

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newses != null && !newses.isEmpty()) {
            mAdapter.addAll(newses);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    public void onRefresh() {
        update();
    }

    /**
     * This method is called in the onRefresh method
     */
    private void update() {

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Restart the loader
            loaderManager.restartLoader(LOADER_ID, null, this);
            mEmptyStateLayout.setVisibility(View.GONE);
        } else {
            Toast.makeText(activity, "No internet connection" ,Toast.LENGTH_LONG).show();
        }
        // Hide the refresh indicator
        mRefreshLayout.setRefreshing(false);
    }
}

