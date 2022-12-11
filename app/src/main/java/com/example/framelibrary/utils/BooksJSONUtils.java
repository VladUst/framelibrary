package com.example.framelibrary.utils;

import com.example.framelibrary.data.books.Book;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BooksJSONUtils {
    private static final String KEY_ITEMS = "items";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE= "title";
    private static final String KEY_AUTHOR = "authors";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_COVER_PATH = "imageLinks";
    private static final String KEY_CATEGORIES = "categories";

    public static ArrayList<Book> getBooksFromJSON(JSONObject jsonObject) {
        ArrayList<Book> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_ITEMS);
            for (int i = 0; i < 6; i++) {
                JSONObject objectBook = jsonArray.getJSONObject(i);
                String id = objectBook.getString(KEY_ID);
                JSONObject objectInfo = objectBook.getJSONObject("volumeInfo");
                String title = objectInfo.getString(KEY_TITLE);
                String description = objectInfo.getString(KEY_DESCRIPTION);
                JSONArray authors = objectInfo.getJSONArray(KEY_AUTHOR);
                String author = authors.getString(0);
                JSONArray categories = objectInfo.getJSONArray(KEY_CATEGORIES);
                String category = categories.getString(0);
                JSONObject coversObjects = objectInfo.getJSONObject(KEY_COVER_PATH);
                String coverPath = coversObjects.getString("thumbnail");
                Book book = new Book(id, title, author, category, description, coverPath);
                result.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
