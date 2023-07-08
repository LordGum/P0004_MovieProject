package com.example.movies.MovieClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("docs")
    private final List<Movie> ListOfMovies;

    public MovieResponse(List<Movie> listOfMovies) {
        ListOfMovies = listOfMovies;
    }

    public List<Movie> getListOfMovies() {
        return ListOfMovies;
    }
}
