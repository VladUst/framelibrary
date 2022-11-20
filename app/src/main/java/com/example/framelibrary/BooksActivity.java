package com.example.framelibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.framelibrary.utils.FetchBooks;

public class BooksActivity extends AppCompatActivity {

    private EditText editInput;
    private TextView textTitle;
    private TextView textAuthor;
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
        editInput = findViewById(R.id.edit_input);
        textAuthor = findViewById(R.id.text_authorFound);
        textTitle = findViewById(R.id.text_titleFound);

    }

    public void onSearchClick(View view) {
        String queryString = editInput.getText().toString();
        System.out.println(queryString);
        new FetchBooks(textTitle, textAuthor).execute(queryString);
    }
}