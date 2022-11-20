package com.example.framelibrary.utils;

import android.os.AsyncTask;
import android.widget.TextView;

public class FetchBooks extends AsyncTask<String, Void, String> {
    private TextView textTitle;
    private TextView textAuthor;

    public FetchBooks(TextView textTitle, TextView textAuthor) {
        this.textTitle = textTitle;
        this.textAuthor = textAuthor;
    }

    @Override
    protected String doInBackground(String... strings) {
        return BooksNetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
