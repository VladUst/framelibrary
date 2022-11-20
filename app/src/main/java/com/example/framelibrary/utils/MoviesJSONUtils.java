package com.example.framelibrary.utils;

import com.example.framelibrary.data.Movie;
import com.example.framelibrary.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MoviesJSONUtils {
    private static final String KEY_RESULTS = "results";
    private static final String KEY_VOTE_COUNT = "vote_count";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE= "title";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_RELEASE_DATE = "release_date";
    public static final String MOVIES_POSTER_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER = "w185";
    public static final String BIG_POSTER = "w780";

    public static final String TRAILER_KEY = "key";
    public static final String TRAILER_NAME = "name";
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    public static ArrayList<Trailer> getTrailersFromJson(JSONObject json){
        ArrayList<Trailer> result = new ArrayList<>();
        if(json == null){
            return result;
        }
        try {
            JSONArray jsonArray = json.getJSONArray(KEY_RESULTS);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonTrailer = jsonArray.getJSONObject(i);
                String url = YOUTUBE_URL + jsonTrailer.getString(TRAILER_KEY);
                String name = jsonTrailer.getString(TRAILER_NAME);
                Trailer trailer = new Trailer(url, name);
                result.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject) {
        ArrayList<Movie> result = new ArrayList<>();
        if (jsonObject == null) {
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                String title = objectMovie.getString(KEY_TITLE);
                String originalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath = MOVIES_POSTER_BASE_URL + SMALL_POSTER + objectMovie.getString(KEY_POSTER_PATH);
                String bigPosterPath = MOVIES_POSTER_BASE_URL + BIG_POSTER + objectMovie.getString(KEY_POSTER_PATH);
                String backdropPath = objectMovie.getString(KEY_BACKDROP_PATH);
                double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = objectMovie.getString(KEY_RELEASE_DATE);
                Movie movie = new Movie(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backdropPath, voteAverage, releaseDate);
                result.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
