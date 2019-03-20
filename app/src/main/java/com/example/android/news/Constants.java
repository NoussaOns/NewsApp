package com.example.android.news;

public class Constants {

    //No constructor
    private Constants(){}

    /**  Extract the key associated with the JSONObject */
    public static final String JSON_KEY_RESPONSE = "response";
    public static final String JSON_KEY_RESULTS = "results";
    public static final String JSON_KEY_WEB_TITLE = "webTitle";
    public static final String JSON_KEY_WEB_PUBLICATION_DATE = "webPublicationDate";
    public static final String JSON_KEY_WEB_URL = "webUrl";
    public static final String JSON_QUERY_KEY_TAGS = "show-tags";
    public static final String JSON_QUERY_KEY_FIELDS = "show-fields";
    public static final String JSON_KEY_TAGS = "tags";
    public static final String JSON_KEY_TAGS_VALUE = "contributor";
    public static final String JSON_KEY_AUTHOR_FIRST_NAME = "firstName";
    public static final String JSON_KEY_AUTHOR_LAST_NAME = "lastName";
    public static final String JSON_KEY_FIELDS = "fields";
    public static final String JSON_KEY_THUMBNAIL = "thumbnail";
    public static final String JSON_KEY_TRAIL_TEXT = "trailText";
    public static final String JSON_KEY_FORMAT = "format";
    public static final String JSON_KEY_FORMAT_VALUE = "json";
    public static final String JSON_KEY_PAGE_SIZE = "page-size";
    public static final String JSON_KEY_SECTION = "section";
    public static final String JSON_KEY_PAGE_SIZE_VALUE = "15";
    public static final String JSON_KEY_API = "api-key";
    public static final String JSON_API_VALUE = "b451104e-3fe1-47df-af47-d968daef9408";


    /**different sections*/
    public static final String JSON_VALUE_POLITICS = "politics";
    public static final String JSON_VALUE_SPORTS = "sport";
    public static final String JSON_VALUE_SCIENCE ="science" ;
    public static final String JSON_VALUE_BUSINESS = "business";

    /** The show fields we want our API to return */
    public static final String SHOW_FIELDS = "thumbnail,trailText";

    /** URL of the data*/
    public static final String DATA_URL = "https://content.guardianapis.com/search";
}
