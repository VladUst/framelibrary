package com.example.framelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity {

    private ImageView image_bookCover;
    private TextView text_title;
    private TextView text_author;
    private TextView text_category;
    private TextView text_description;
    private String id;
    private String title;
    private String author;
    private String category;
    private String description;
    private String coverPath;

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
        setContentView(R.layout.activity_book_detail);
        image_bookCover = findViewById(R.id.image_bookCover);
        text_title = findViewById(R.id.text_titleFound);
        text_author = findViewById(R.id.text_authorFound);
        text_category = findViewById(R.id.text_category);
        text_description = findViewById(R.id.text_description);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getStringExtra("id");
            title = intent.getStringExtra("title");
            author = intent.getStringExtra("author");
            category = intent.getStringExtra("category");
            description = intent.getStringExtra("description");
            coverPath = intent.getStringExtra("coverPath");
        } else {
            finish();
        }
        Picasso.get().load(coverPath).into(image_bookCover);
        text_title.setText(title);
        text_author.setText(author);
        text_category.setText(category);
        text_description.setText(description);
    }

    public void onChangeFavorite(View view) {
    }
}