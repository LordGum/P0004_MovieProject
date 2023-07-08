package com.example.movies.MovieActivityPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersHolders> {
    private List<Trailer> trailers = new ArrayList<>();
    private OnClickTrailerListener onClickTrailerListener;

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public void setOnClickTrailerListener(OnClickTrailerListener onClickTrailerListener) {
        this.onClickTrailerListener = onClickTrailerListener;
    }

    @NonNull
    @Override
    public TrailersHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_item,
                parent,
                false
        );
        return new TrailersHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersHolders holder, int position) {
        Trailer trailer = trailers.get(position);

        holder.textViewName.setText(trailer.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickTrailerListener != null) {
                    onClickTrailerListener.OnClick(trailer);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return trailers.size();
    }

    static class TrailersHolders extends RecyclerView.ViewHolder {
        private final TextView textViewName;

        public TrailersHolders(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.trailerView);
        }
    }

    public interface OnClickTrailerListener{
        void OnClick(Trailer trailer);
    }
}
