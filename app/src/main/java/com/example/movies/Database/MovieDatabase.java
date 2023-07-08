package com.example.movies.Database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movies.MovieClass.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase{

    private static final String NAME_BD = "movie.db";
    private static MovieDatabase instance;

    public static MovieDatabase getInstance(Application application) {
        if(instance == null) {
            instance = Room.databaseBuilder(
                    application,
                    MovieDatabase.class,
                    NAME_BD
            ).build();
        }
        return instance;
    }

    public abstract MovieDao movieDao();
}
