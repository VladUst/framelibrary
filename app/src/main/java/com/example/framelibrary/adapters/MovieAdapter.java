package com.example.framelibrary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framelibrary.R;
import com.example.framelibrary.data.movies.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private List<Movie> movies;
    private OnPosterClickListener onPosterClickListener;
    private OnEndListListener onListEndListener;

    public MovieAdapter(){
        movies = new ArrayList<>();
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public void setOnListEndListener(OnEndListListener onListEndListener) {
        this.onListEndListener = onListEndListener;
    }

    public interface OnPosterClickListener{
        void onPosterClick(int position);
    }

    public interface OnEndListListener {
        void uploadNewData();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        if(position > movies.size() - 4 && onListEndListener != null){
            onListEndListener.uploadNewData();
        }
        Movie movie = movies.get(position);
        ImageView imageView = holder.imageViewSmallPoster;
        Picasso.get().load(movie.getPosterPath()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewSmallPoster;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
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
        this.movies.clear();
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> movies){
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }
}
