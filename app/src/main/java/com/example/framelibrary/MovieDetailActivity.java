package com.example.framelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.framelibrary.adapters.RelatedBooksAdapter;
import com.example.framelibrary.adapters.TrailerAdapter;
import com.example.framelibrary.data.books.Book;
import com.example.framelibrary.data.movies.FavoriteMovie;
import com.example.framelibrary.data.movies.Movie;
import com.example.framelibrary.data.movies.MovieController;
import com.example.framelibrary.data.movies.Trailer;
import com.example.framelibrary.utils.BooksJSONUtils;
import com.example.framelibrary.utils.BooksNetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView imageViewAddToFavorite;
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewRate;
    private TextView textViewDate;
    private TextView textViewOverview;
    private TextView textViewTrailerName;
    private ScrollView scrollViewInfo;
    private FavoriteMovie favoriteMovie;
    private Movie movie;
    private Trailer trailer;
    private int id;
    private MovieController movieController;
    private RecyclerView recyclerViewTrailers;
    private TrailerAdapter trailerAdapter;
    private RecyclerView recyclerViewRelatedBooks;
    private RelatedBooksAdapter relatedBooksAdapter;
    RequestQueue relatedBooksRequestQueue;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itemMovies:
                Intent moviesIntent = new Intent(this, MainActivity.class);
                startActivity(moviesIntent);
                break;
            case R.id.itemBooks:
                Intent booksIntent = new Intent(this, BooksActivity.class);
                startActivity(booksIntent);
                break;
            case R.id.itemFavorite:
                Intent favoriteIntent = new Intent(this, FavoriteActivity.class);
                startActivity(favoriteIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        imageViewBigPoster = findViewById(R.id.image_bookCover);
        imageViewAddToFavorite = findViewById(R.id.image_addToFavorite);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDate = findViewById(R.id.textViewDate);
        textViewRate = findViewById(R.id.textViewRate);
        textViewOverview = findViewById(R.id.textViewOverview);
        textViewTrailerName = findViewById(R.id.textViewTrailerName);
        scrollViewInfo = findViewById(R.id.scrollViewInfo);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            finish();
        }
        movieController = ViewModelProviders.of(this).get(MovieController.class);
        movie = movieController.getMovieById(id);
        trailer = new Trailer("https://www.youtube.com/watch?v=0vxOhd4qlnA", "Official Teaser");
        System.out.println(movie.getDatabaseID());
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewTitle.setText(movie.getOriginalTitle());
        textViewOverview.setText(movie.getOverview());
        textViewDate.setText(movie.getReleaseDate());
        textViewRate.setText(Double.toString(movie.getVoteAverage()));
        textViewTrailerName.setText(trailer.getName());
        setFavorite();
        relatedBooksRequestQueue = Volley.newRequestQueue(this);
        recyclerViewRelatedBooks = findViewById(R.id.recyclerViewRelatedBooks);
        relatedBooksAdapter = new RelatedBooksAdapter();
        relatedBooksAdapter.setOnBookClickListener(new RelatedBooksAdapter.OnBookClickListener() {
            @Override
            public void onBookClick(int position) {
                Book book = relatedBooksAdapter.getRelatedBooks().get(position);
                Intent intent = new Intent(MovieDetailActivity.this, BookDetailActivity.class);
                intent.putExtra("id", book.getId());
                intent.putExtra("title", book.getTitle());
                intent.putExtra("author", book.getAuthor());
                intent.putExtra("description", book.getDescription());
                intent.putExtra("category", book.getCategory());
                intent.putExtra("coverPath", book.getCoverPath());
                startActivity(intent);
            }
        });
        recyclerViewRelatedBooks.setLayoutManager(new LinearLayoutManager(this));
        URL url = BooksNetworkUtils.buildBooksURL(movie.getTitle());
        processBooksRequest(url.toString());
        /*recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        trailerAdapter = new TrailerAdapter();
        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Toast.makeText(MovieDetailActivity.this, url, Toast.LENGTH_SHORT).show();
                *//*Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intentToTrailer);*//*
            }
        });
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setAdapter(trailerAdapter);*/
        /*JSONObject jsonTrailers = NetworkUtils.getTrailersJSON(movie.getId());
        ArrayList<Trailer> trailers = JSONMoviesUtils.getTrailersFromJson(jsonTrailers);
        trailerAdapter.setTrailers(trailers);*/
        scrollViewInfo.smoothScrollTo(0, 0);
    }

    public void onTrailerClick(View view) {
        Toast.makeText(MovieDetailActivity.this, "Trailer clicked", Toast.LENGTH_SHORT).show();
        Uri trailer_address = Uri.parse(trailer.getUrl());
        Intent intentToTrailer = new Intent(Intent.ACTION_VIEW, trailer_address);
        startActivity(intentToTrailer);
    }

    public void onChangeFavorite(View view) {
        if(favoriteMovie == null){
            movieController.insertFavoriteMovie(new FavoriteMovie(movie));
            Toast.makeText(this, R.string.add_to_favorite, Toast.LENGTH_SHORT).show();
        } else{
            movieController.deleteFavoriteMovie(favoriteMovie);
            Toast.makeText(this, R.string.remove_from_favorite, Toast.LENGTH_SHORT).show();
        }
        setFavorite();
    }
    private void setFavorite(){
        favoriteMovie = movieController.getFavoriteMovieById(id);
        if(favoriteMovie == null){
            imageViewAddToFavorite.setImageResource(R.drawable.favourite_add_to);
        } else{
            imageViewAddToFavorite.setImageResource(R.drawable.favourite_remove);
        }
    }

    private void processBooksRequest(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Book> books = BooksJSONUtils.getBooksFromJSON(response);
                StringBuilder builder = new StringBuilder();
                for(Book book : books){
                    builder.append(book.getCoverPath()).append("\n");
                }
                Log.i("Books", builder.toString());
                recyclerViewRelatedBooks.setAdapter(relatedBooksAdapter);
                relatedBooksAdapter.setRelatedBooks(books);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        relatedBooksRequestQueue.add(request);
    }
}