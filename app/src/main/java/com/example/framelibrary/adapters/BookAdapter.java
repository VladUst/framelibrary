package com.example.framelibrary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framelibrary.R;
import com.example.framelibrary.data.books.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    private List<Book> books;
    private MovieAdapter.OnPosterClickListener onPosterClickListener;

    public BookAdapter(){
        books = new ArrayList<>();
    }

    public void setOnPosterClickListener(MovieAdapter.OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public interface OnPosterClickListener{
        void onPosterClick(int position);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        Book book = books.get(position);
        ImageView imageView = holder.imageViewBookCover;
        System.out.println(book.getCoverPath());
        Picasso.get().load(book.getCoverPath()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class BookViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewBookCover;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewBookCover = itemView.findViewById(R.id.imageViewBookCover);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onPosterClickListener != null){
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void clear() {
        this.books.clear();
        notifyDataSetChanged();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }
}
