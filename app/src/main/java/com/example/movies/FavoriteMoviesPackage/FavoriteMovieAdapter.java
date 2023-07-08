package com.example.movies.FavoriteMoviesPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.MovieClass.Movie;
import com.example.movies.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieHolder> {

    private List<Movie> movies = new ArrayList<>();
    private OnClickMovieListener onClickMovieListener;

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnClickMovieListener(OnClickMovieListener onClickMovieListener) {
        this.onClickMovieListener = onClickMovieListener;
    }

    @NonNull
    @Override
    public FavoriteMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false
        );
        return new FavoriteMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.imageViewPoster);

        holder.rating.setText(movie.getRating().getKp());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickMovieListener.onClickMovie(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class FavoriteMovieHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewPoster;
        private TextView rating;

        FavoriteMovieHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imagePoster);
            rating = itemView.findViewById(R.id.rating);
        }
    }

    interface OnClickMovieListener {
        void onClickMovie(Movie movie);
    }
}
