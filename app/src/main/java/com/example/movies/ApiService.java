package com.example.movies;

import com.example.movies.CommentsPackage.CommentResponse;
import com.example.movies.MovieActivityPackage.TrailerResponse;
import com.example.movies.MovieClass.MovieResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("v1.3/movie?sortType=-1&limit=30&rating.kp=5-10&top250=&isSeries=false&token=VTJ19D1-AN9435V-H7ZWADQ-0MZHQ45")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("v1.3/movie/{id}?token=VTJ19D1-AN9435V-H7ZWADQ-0MZHQ45")
    Single<TrailerResponse> loadTrailersApi(@Path("id") int id);

    @GET("v1/review?page=1&limit=10&token=VTJ19D1-AN9435V-H7ZWADQ-0MZHQ45&field=movieId")
    Single<CommentResponse> loadCommentApi(@Query("search") int id);
}
