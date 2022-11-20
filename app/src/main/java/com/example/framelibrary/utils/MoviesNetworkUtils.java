package com.example.framelibrary.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MoviesNetworkUtils {
    private static final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String TRAILERS_BASE_URL = "https://api.themoviedb.org/3/movie/%s/videos";

    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";

    private static final String MOVIES_API_KEY="5fa82f6b06c8ebd9a76f5e67dca170c1";
    private static final String VALUE_LANGUAGE = "en-US";
    private static final String VALUE_SORT_POPULAR = "popularity.desc";
    private static final String VALUE_SORT_RATED = "vote_count.desc";

    public static final int POPULARITY = 0;
    public static final int RATED = 1;

    private static URL buildURLToTrailers(int id) {
        Uri uri = Uri.parse(String.format(TRAILERS_BASE_URL, id)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, MOVIES_API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, VALUE_LANGUAGE).build();
        try{
            return new URL(uri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getTrailersJSON(int id) {
        JSONObject result = null;
        URL url = buildURLToTrailers(id);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static URL buildMoviesURL(int sortBy, int page) {
        URL result = null;
        String methodOfSort;
        if (sortBy == POPULARITY) {
            methodOfSort = VALUE_SORT_POPULAR;
        } else {
            methodOfSort = VALUE_SORT_RATED;
        }
        Uri uri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, MOVIES_API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, VALUE_LANGUAGE)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getMoviesJSON(int sortBy, int page) {
        JSONObject result = null;
        URL url = buildMoviesURL(sortBy, page);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            JSONObject result = null;
            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpsURLConnection connection = null;
            System.out.println("before connection");
            try {
                connection = (HttpsURLConnection) urls[0].openConnection();
                System.out.println("connect to: " + connection);
                InputStream inputStream = connection.getInputStream();
                System.out.println("input stream: "+inputStream);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }
    }
}
