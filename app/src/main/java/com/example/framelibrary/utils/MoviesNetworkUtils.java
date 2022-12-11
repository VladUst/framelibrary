package com.example.framelibrary.utils;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.framelibrary.data.movies.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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

    /*public static JSONObject getTrailersJSON(int id) {
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
    }*/

    public static URL buildMoviesURL(int sortBy, int page) {
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
}
