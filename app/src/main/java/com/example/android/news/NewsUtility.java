package com.example.android.news;

import android.content.Context;
import android.net.Uri;

public class NewsUtility {

    private NewsUtility(){
    }

    /**
     * Get the uri needed
     * @param context
     * @return
     */
    public static Uri.Builder getPreferredUri(Context context){
        Uri baseUri = Uri.parse(Constants.DATA_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();

        //append the request url to the required parameters
        //the format is json
        uriBuilder.appendQueryParameter(Constants.JSON_KEY_FORMAT,Constants.JSON_KEY_FORMAT_VALUE);

        //show up to the give number of pages
        uriBuilder.appendQueryParameter(Constants.JSON_KEY_PAGE_SIZE,Constants.JSON_KEY_PAGE_SIZE_VALUE);

        // show tags
        uriBuilder.appendQueryParameter(Constants.JSON_QUERY_KEY_TAGS,Constants.JSON_KEY_TAGS_VALUE);

        //show thumbnail and trail text fields
        uriBuilder.appendQueryParameter(Constants.JSON_QUERY_KEY_FIELDS,Constants.SHOW_FIELDS);

        //my api
        uriBuilder.appendQueryParameter(Constants.JSON_KEY_API,Constants.JSON_API_VALUE);

        return uriBuilder;
    }

    public static String getPreferredUrl(Context context, String section){
        Uri.Builder uriBuilder = getPreferredUri(context);
        return uriBuilder.appendQueryParameter(Constants.JSON_KEY_SECTION,section).toString();
    }
}
