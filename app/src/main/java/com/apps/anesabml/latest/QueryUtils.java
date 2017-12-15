package com.apps.anesabml.latest;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Helper methods related to requesting and receiving news data from the guardian.
 */
public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because and an object instance of QueryUtils is not needed.
     */
    private QueryUtils() {
    }

    /**
     * Retrieve the data from the Guardian and return a list of {@link News}.
     */
    public static List<News> fetchNewsData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<News> newses = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return newses;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        OkHttpClient client = null;

        try {
            client = new OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            jsonResponse = response.body().string();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }
        return jsonResponse;
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<News> extractFeatureFromJson(String newsJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding news to
        List<News> newses = new ArrayList<>();

        try {
            // Create a JSONObject from the JSON response string
            JSONObject jsonObject = new JSONObject(newsJSON);

            // Get JsonObject associated with key "response"
            JSONObject response = jsonObject.getJSONObject("response");

            // Get JsonArray associated with key "results"
            JSONArray results = response.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                // Get the JsonObject at index i
                JSONObject newsInfo = results.getJSONObject(i);

                // Extract the value with key "sectionName"
                String section = newsInfo.getString("sectionName");
                // Extract the value with key "webTitle"
                String title = newsInfo.getString("webTitle");
                // Extract the value with key "webPublicationDate"
                String publicationDate = newsInfo.getString("webPublicationDate");
                publicationDate = publicationDate.substring(11, 16);
                // Extract the JsonArray with key "fields"
                JSONObject fields = newsInfo.getJSONObject("fields");
                // Extract the value with key "thumbnail"
                String imageLink = fields.getString("thumbnail");

                // Create a new {@link News} object
                News news = new News(imageLink, title, section, publicationDate);

                // Add the {@link News} object to the list
                newses.add(news);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return the list of news
        return newses;
    }

}