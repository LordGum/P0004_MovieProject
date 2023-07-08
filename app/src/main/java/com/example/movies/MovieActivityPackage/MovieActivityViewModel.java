package com.example.movies.MovieActivityPackage;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movies.ApiFactory;
import com.example.movies.CommentsPackage.Comment;
import com.example.movies.CommentsPackage.CommentResponse;
import com.example.movies.Database.MovieDao;
import com.example.movies.Database.MovieDatabase;
import com.example.movies.MovieClass.Movie;
import com.example.movies.MovieClass.MovieResponse;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieActivityViewModel extends AndroidViewModel {

    CompositeDisposable compositeDisposable =new CompositeDisposable();
    private static final String TAG = "MovieActivityViewModel";

    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private final MutableLiveData<List<Comment>> comments = new MutableLiveData<>();

    private final MovieDao movieDao;

    public MovieActivityViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).movieDao();
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }
    public LiveData<List<Comment>> getComments() {
        return comments;
    }


    private Single<TrailerResponse> loadTrailerRx(int id) {
        return ApiFactory.apiService.loadTrailersApi(id);
    }
    public void loadTrailer(int id) {
        Disposable disposable = loadTrailerRx(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TrailerResponse>() {
                    @Override
                    public void accept(TrailerResponse trailerResponse) throws Throwable {
                        trailers.setValue(trailerResponse.getTrailersList().getTrailers());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }



    private Single<CommentResponse> loadCommentsRx(int id) {
        return ApiFactory.getApiService().loadCommentApi(id);
    }
    public void loadComments(int id) {
        Disposable disposable = loadCommentsRx(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommentResponse>() {
                    @Override
                    public void accept(CommentResponse commentResponse) throws Throwable {
                        comments.setValue(commentResponse.getCommentsList());
                        Log.d(TAG, "все ок");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, "проблема в MovieActivityViewModel");
                    }
                });
    }



    public LiveData<Movie> getFavoriteMovieVM(int movieId) {
        return movieDao.getFavoriteMovie(movieId);
    }

    public void addFavoriteMovieVM(Movie movie) {
        Disposable disposable = movieDao.addFavoriteMovie(movie)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    public void removeFavoriteMovieVM(int movieId) {
        Disposable disposable = movieDao.removeFavoriteMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
