package com.example.framelibrary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framelibrary.R;
import com.example.framelibrary.data.books.Book;

import java.util.ArrayList;
import java.util.List;

public class RelatedBooksAdapter extends RecyclerView.Adapter<RelatedBooksAdapter.relatedBooksViewHolder>{

    private ArrayList<Book> relatedBooks;
    private OnBookClickListener onBookClickListener;
    @NonNull
    @Override
    public relatedBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.related_book_item, parent, false);
        return new relatedBooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull relatedBooksViewHolder holder, int position) {
        Book book = relatedBooks.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
    }

    @Override
    public int getItemCount() {
        return relatedBooks.size();
    }

    public interface OnBookClickListener{
        void onBookClick(int position);
    }

    class relatedBooksViewHolder extends RecyclerView.ViewHolder {

        private TextView bookTitle;
        private TextView bookAuthor;
        public relatedBooksViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = itemView.findViewById(R.id.text_bookTitle);
            bookAuthor = itemView.findViewById(R.id.text_bookAuthor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onBookClickListener != null){
                        onBookClickListener.onBookClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public void setRelatedBooks(ArrayList<Book> books) {
        this.relatedBooks = books;
        notifyDataSetChanged();
    }

    public List<Book> getRelatedBooks() {
        return relatedBooks;
    }

    public void setOnBookClickListener(OnBookClickListener onBookClickListener) {
        this.onBookClickListener = onBookClickListener;
    }
}
