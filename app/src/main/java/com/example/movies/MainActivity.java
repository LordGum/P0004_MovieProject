package com.example.movies;

import static com.example.movies.MovieActivity.newIntent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.movies.Database.MovieDao;
import com.example.movies.Database.MovieDatabase;
import com.example.movies.FavoriteMoviesPackage.FavoriteMoviesActivity;
import com.example.movies.MovieClass.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String tag = "MainActivityTAG";

    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getMovieResponse().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
        mainViewModel.loadMovies();
        reachEnd();
        clickMovie();


    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        movieAdapter = new MovieAdapter();
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void reachEnd() {
        movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
            @Override
            public void onReachEnd() {
                mainViewModel.loadMovies();
            }
        });
    }

    private void clickMovie() {
        movieAdapter.setOnClickMovieListener(new MovieAdapter.OnClickMovieListener() {
            @Override
            public void onClickMovie(Movie movie) {
                Intent intent = newIntent(MainActivity.this, movie);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_menu) {
            Intent intent = new Intent(MainActivity.this,FavoriteMoviesActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}