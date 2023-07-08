package com.example.movies.FavoriteMoviesPackage;

import static com.example.movies.MovieActivity.newIntent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.movies.MovieAdapter;
import com.example.movies.MovieClass.Movie;
import com.example.movies.R;

import java.util.List;

public class FavoriteMoviesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoriteMovieAdapter favoriteMovieAdapter;
    private FavoriteMoviesViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_movies);

        init();
        clickMovie();
        setFavoriteMovies();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerViewFM);

        favoriteMovieAdapter = new FavoriteMovieAdapter();
        recyclerView.setAdapter(favoriteMovieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        viewModel = new ViewModelProvider(this).get(FavoriteMoviesViewModel.class);
    }

    private void clickMovie() {
        favoriteMovieAdapter.setOnClickMovieListener(new FavoriteMovieAdapter.OnClickMovieListener() {
            @Override
            public void onClickMovie(Movie movie) {
                Intent intent = newIntent(FavoriteMoviesActivity.this, movie);
                startActivity(intent);
            }
        });
    }

    private void setFavoriteMovies() {
        viewModel.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                favoriteMovieAdapter.setMovies(movies);
            }
        });
    }
}