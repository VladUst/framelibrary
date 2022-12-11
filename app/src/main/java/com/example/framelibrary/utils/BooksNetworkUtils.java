package com.example.framelibrary.utils;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BooksNetworkUtils {
    private static final String BOOKS_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q";
    private static final String API_PARAM = "key";
    private static final String MAX_RESULTS = "maxResults";
    private static final String PRINT_TYPE = "printType";
    private static final String API_KEY = "AIzaSyBAOEz_drcKhn-LL6_medHyuLkK9wPsoBg";
    public static URL buildBooksURL(String queryString) {
        String queryMode = "intitle:"+queryString;
        Uri buildUri = Uri.parse(BOOKS_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryMode)
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(MAX_RESULTS, "10")
                .appendQueryParameter(PRINT_TYPE, "books")
                .build();
        URL requestURL = null;
        try {
            requestURL = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return requestURL;
    }
}
