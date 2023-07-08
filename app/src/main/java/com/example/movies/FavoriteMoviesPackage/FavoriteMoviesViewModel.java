package com.example.movies.FavoriteMoviesPackage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.Database.MovieDao;
import com.example.movies.Database.MovieDatabase;
import com.example.movies.MovieClass.Movie;

import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class FavoriteMoviesViewModel extends AndroidViewModel {

    private MovieDao movieDao;

    public FavoriteMoviesViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).movieDao();
    }

    public LiveData<List<Movie>> getFavoriteMovies() {
        return movieDao.getListOfFavoriteMovies();
    }
}
