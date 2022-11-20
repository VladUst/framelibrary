package com.example.framelibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framelibrary.adapters.MovieAdapter;
import com.example.framelibrary.data.Movie;
import com.example.framelibrary.data.MovieViewModel;
import com.example.framelibrary.utils.MoviesJSONUtils;
import com.example.framelibrary.utils.MoviesNetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
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

    private int getCountColumn() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels / displayMetrics.density);
        return width/185 > 2 ? width/185 : 2;
    }

    private Switch switchMovieSort;
    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapter;
    private TextView textViewRated;
    private TextView textViewPopular;

    private MovieViewModel movieViewModel;
    private static int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        switchMovieSort = findViewById(R.id.switchMovieSort);
        textViewPopular = findViewById(R.id.textViewPopular);
        textViewRated = findViewById(R.id.textViewRated);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, getCountColumn()));
        movieAdapter = new MovieAdapter();
        recyclerViewPosters.setAdapter(movieAdapter);
        switchMovieSort.setChecked(true);
        switchMovieSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setFilterMethod(isChecked);
            }
        });
        switchMovieSort.setChecked(false);
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                //Toast.makeText(MainActivity.this, "Clicked: " + position, Toast.LENGTH_SHORT).show();
                Movie movie = movieAdapter.getMovies().get(position);
                Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });
        movieAdapter.setOnListEndListener(new MovieAdapter.OnEndListListener() {
            @Override
            public void uploadNewData() {
                Toast.makeText(MainActivity.this, "End", Toast.LENGTH_SHORT).show();
            }
        });
        LiveData<List<Movie>> moviesFromLiveData = movieViewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
        /*Log.i("start", "starting app");
        JSONObject moviesJSON = NetworkUtils.getMoviesJSON(NetworkUtils.POPULARITY, 1);
        Log.i("Movies list", "???");
        ArrayList<Movie> movies = JSONMoviesUtils.getMoviesFromJSON(moviesJSON);
        StringBuilder builder = new StringBuilder();
        for(Movie movie : movies){
            builder.append(movie.getTitle()).append("\n");
        }
        Log.i("Result", builder.toString());*/
    }

    public void onSetRated(View view) {
        setFilterMethod(false);
        switchMovieSort.setChecked(false);
    }

    public void onSetPopular(View view) {
        setFilterMethod(true);
        switchMovieSort.setChecked(true);
    }

    private void setFilterMethod(boolean isPopular){
        int filterMethod;
        if(isPopular){
            textViewPopular.setTextColor(getResources().getColor(R.color.filter_active));
            textViewRated.setTextColor(getResources().getColor(R.color.white));
            filterMethod = MoviesNetworkUtils.POPULARITY;
        } else {
            textViewRated.setTextColor(getResources().getColor(R.color.filter_active));
            textViewPopular.setTextColor(getResources().getColor(R.color.white));
            filterMethod = MoviesNetworkUtils.RATED;
        }
        downloadData(filterMethod, page);
    }

     private void downloadData(int filterMethod, int page){
        JSONObject moviesJSON = MoviesNetworkUtils.getMoviesJSON(filterMethod, page);
        ArrayList<Movie> movies = MoviesJSONUtils.getMoviesFromJSON(moviesJSON);
        if(movies != null && !movies.isEmpty()){
            movieViewModel.deleteAllMovies();
            for(Movie movie: movies){
                movieViewModel.insertMovie(movie);
            }
        }
    }
}