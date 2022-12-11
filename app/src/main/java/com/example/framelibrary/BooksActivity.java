package com.example.framelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.framelibrary.adapters.BookAdapter;
import com.example.framelibrary.adapters.MovieAdapter;
import com.example.framelibrary.data.books.Book;
import com.example.framelibrary.data.movies.Movie;
import com.example.framelibrary.utils.BooksJSONUtils;
import com.example.framelibrary.utils.BooksNetworkUtils;
import com.example.framelibrary.utils.MoviesJSONUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class BooksActivity extends AppCompatActivity {

    private EditText searchInput;
    private RecyclerView recyclerBooks;
    private BookAdapter bookAdapter;
    RequestQueue bookRequestQueue;
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
        setContentView(R.layout.activity_books);
        bookRequestQueue = Volley.newRequestQueue(this);
        searchInput = findViewById(R.id.edit_input);
        recyclerBooks = findViewById(R.id.recyclerFoundedBooks);
        recyclerBooks.setLayoutManager(new GridLayoutManager(this, 2));
        bookAdapter = new BookAdapter();
        recyclerBooks.setAdapter(bookAdapter);
        bookAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Book book = bookAdapter.getBooks().get(position);
                Intent intent = new Intent(BooksActivity.this, BookDetailActivity.class);
                intent.putExtra("id", book.getId());
                intent.putExtra("title", book.getTitle());
                intent.putExtra("author", book.getAuthor());
                intent.putExtra("description", book.getDescription());
                intent.putExtra("category", book.getCategory());
                intent.putExtra("coverPath", book.getCoverPath());
                startActivity(intent);
            }
        });
    }

    public void onSearchClick(View view) {
        String queryString = searchInput.getText().toString();
        URL url = BooksNetworkUtils.buildBooksURL(queryString);
        processBooksRequest(url.toString());
    }

    private void processBooksRequest(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Book> books = BooksJSONUtils.getBooksFromJSON(response);
                bookAdapter.setBooks(books);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        bookRequestQueue.add(request);
    }
}