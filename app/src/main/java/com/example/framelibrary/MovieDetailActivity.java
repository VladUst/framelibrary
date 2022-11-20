package com.example.framelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framelibrary.adapters.TrailerAdapter;
import com.example.framelibrary.data.FavoriteMovie;
import com.example.framelibrary.data.Movie;
import com.example.framelibrary.data.MovieViewModel;
import com.example.framelibrary.data.Trailer;
import com.squareup.picasso.Picasso;

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
    private MovieViewModel movieViewModel;
    private RecyclerView recyclerViewTrailers;
    private TrailerAdapter trailerAdapter;
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
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movie = movieViewModel.getMovieById(id);
        trailer = new Trailer("https://www.youtube.com/watch?v=0vxOhd4qlnA", "Official Teaser(2014)");
        System.out.println(movie.getDatabaseID());
        Picasso.get().load(movie.getBigPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewTitle.setText(movie.getOriginalTitle());
        textViewOverview.setText(movie.getOverview());
        textViewDate.setText(movie.getReleaseDate());
        textViewRate.setText(Double.toString(movie.getVoteAverage()));
        textViewTrailerName.setText(trailer.getName());
        setFavorite();
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

    public void onRelatedBookClick(View view) {
        Toast.makeText(MovieDetailActivity.this, "Book clicked", Toast.LENGTH_SHORT).show();
        Intent intentToTrailer = new Intent(this, BookDetailActivity.class);
        startActivity(intentToTrailer);
    }

    public void onChangeFavorite(View view) {
        if(favoriteMovie == null){
            movieViewModel.insertFavoriteMovie(new FavoriteMovie(movie));
            Toast.makeText(this, R.string.add_to_favorite, Toast.LENGTH_SHORT).show();
        } else{
            movieViewModel.deleteFavoriteMovie(favoriteMovie);
            Toast.makeText(this, R.string.remove_from_favorite, Toast.LENGTH_SHORT).show();
        }
        setFavorite();
    }
    private void setFavorite(){
        favoriteMovie = movieViewModel.getFavoriteMovieById(id);
        if(favoriteMovie == null){
            imageViewAddToFavorite.setImageResource(R.drawable.favourite_add_to);
        } else{
            imageViewAddToFavorite.setImageResource(R.drawable.favourite_remove);
        }
    }

}