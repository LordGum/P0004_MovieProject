package com.example.movies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.MovieClass.Movie;
import com.example.movies.MovieClass.MovieResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final String tag = "MainViewModelTAG";
    private static int page = 1;

    public MutableLiveData<List<Movie>> movieResponse = new MutableLiveData<>();
    public LiveData<List<Movie>> getMovieResponse() {
        return movieResponse;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    private static Single<MovieResponse> loadMoviesRx() {
        return ApiFactory.getApiService().loadMovies(page);
    }

    public void loadMovies() {
        Disposable disposable = loadMoviesRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponseLoaded) throws Throwable {
                        List<Movie> oldMovieResponse = movieResponse.getValue();
                        if(oldMovieResponse != null) {
                            oldMovieResponse.addAll(movieResponseLoaded.getListOfMovies());
                            movieResponse.setValue(oldMovieResponse);
                        } else {
                            movieResponse.setValue(movieResponseLoaded.getListOfMovies());
                        }
                        page++;
                    }
                });

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
