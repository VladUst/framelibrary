package com.example.framelibrary.utils;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class BooksNetworkUtils {
    private static final String BOOKS_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q";
    private static final String API_PARAM = "key";
    private static final String MAX_RESULTS = "maxResults";
    private static final String PRINT_TYPE = "printType";
    private static final String API_KEY = "AIzaSyBAOEz_drcKhn-LL6_medHyuLkK9wPsoBg";
    static String getBookInfo(String queryString){
        HttpsURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonBook = null;
        try {
            Uri buildUri = Uri.parse(BOOKS_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(API_PARAM, API_KEY)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
            URL requestURL = new URL(buildUri.toString());
            urlConnection = (HttpsURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            System.out.println(urlConnection);
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }
            if(buffer.length() == 0){
                return null;
            }
            jsonBook = buffer.toString();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            System.out.println(jsonBook);
            return jsonBook;
        }
    }
}
