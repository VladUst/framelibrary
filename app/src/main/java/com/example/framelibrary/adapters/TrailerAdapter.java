package com.example.framelibrary.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framelibrary.R;
import com.example.framelibrary.data.movies.Trailer;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    private ArrayList<Trailer> trailers;
    private OnTrailerClickListener onTrailerClickListener;
    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.textViewTrailerName.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public interface OnTrailerClickListener{
        void onTrailerClick(String url);
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTrailerName;
        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrailerName = itemView.findViewById(R.id.textViewTrailerName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onTrailerClickListener != null){
                        onTrailerClickListener.onTrailerClick(trailers.get(getAdapterPosition()).getUrl());
                    }
                }
            });
        }
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }
}
