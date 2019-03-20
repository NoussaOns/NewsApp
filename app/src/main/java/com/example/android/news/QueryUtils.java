package com.example.android.news;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    // prevent creatong an empty constructor
    private QueryUtils(){

    }

    public static List<News> fetchNewsData(String requestUrl){
        // create the url object
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
            //Log.e("Response", jsonResponse);
        } catch (IOException e){
            Log.e("QueryUtils", "Problem making the HTTP request.", e);
        }

        return extractFeaturesFromJson(jsonResponse);
    }

    /**
     * Convert the given string url to a url object
     * @param stringUrl to be converted
     * @return the url after conversion
     */
    private static URL createUrl(String stringUrl){
        URL url = null;
        try{
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e("QueryUtils", "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request and return a string containing the json string
     * @param url that we want to fech data from
     * @return the json response
     * @throws IOException
     */

    //make the http request
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if (url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //if the rquest is successful then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStram(inputStream);
            } else {
                Log.e("QueryUtils","Error response code" + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e("QueryUtils", "Problem retrieving the news JSON results.", e);
        } finally {
            //disconnect in all cases
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            //close the input stram
            if (inputStream != null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStram(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeaturesFromJson(String newsJsonString){
        // if json string is empty or null
        if (newsJsonString.equals("") || newsJsonString == null){
            return null;
        }

        // create a list to hold news objects
        List<News> news = new ArrayList<>();

        JSONObject baseJsonResponse = null;
        try {
            baseJsonResponse = new JSONObject(newsJsonString);

            // Extract the JSONObject associated with the key called "response"
            JSONObject responseJsonObject = baseJsonResponse.getJSONObject(Constants.JSON_KEY_RESPONSE);

            // Extract the JSONArray associated with the key called "results"
            JSONArray newsArray = responseJsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);


            for (int i = 0; i < newsArray.length(); i++){
                //get a single news item
                JSONObject jsonObject1 = newsArray.getJSONObject(i);

                //get the news web url
                String newsUrl = jsonObject1.getString(Constants.JSON_KEY_WEB_URL);

                //get the news title
                String newsTitle = jsonObject1.getString(Constants.JSON_KEY_WEB_TITLE);

                //get the publication date
                String newsPublicationDate = jsonObject1.getString(Constants.JSON_KEY_WEB_PUBLICATION_DATE);

                //get the section name
                //String newsSectionName = jsonObject1.getString(Constants.JSON_KEY_SECTION_NAME);

                JSONArray tagsArray = jsonObject1.getJSONArray(Constants.JSON_KEY_TAGS);

                String newsAuthor = null;
                //get the author's name
                if (tagsArray.length()>0){
                    JSONObject jsonObjectTags = tagsArray.getJSONObject(0);
                    String newsAuthorFirstName = jsonObjectTags.getString(Constants.JSON_KEY_AUTHOR_FIRST_NAME);
                    String newsAuthorLastName = jsonObjectTags.getString(Constants.JSON_KEY_AUTHOR_LAST_NAME);
                    newsAuthor = newsAuthorFirstName + " " + newsAuthorLastName;
                }


                //get the image thumbnail url
                String newsImageUrl = jsonObject1.getJSONObject(Constants.JSON_KEY_FIELDS).getString(Constants.JSON_KEY_THUMBNAIL);

                //get the trail text
                String newsTrailText = jsonObject1.getJSONObject(Constants.JSON_KEY_FIELDS).getString(Constants.JSON_KEY_TRAIL_TEXT);

                News newsItem = new News(newsImageUrl,newsTitle,newsTrailText, newsAuthor, newsPublicationDate,newsUrl);

                news.add(newsItem);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return news;
    }
}
