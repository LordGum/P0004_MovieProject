package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.movies.CommentsPackage.Comment;
import com.example.movies.CommentsPackage.CommentAdapter;
import com.example.movies.Database.MovieDao;
import com.example.movies.Database.MovieDatabase;
import com.example.movies.MovieActivityPackage.MovieActivityViewModel;
import com.example.movies.MovieActivityPackage.Trailer;
import com.example.movies.MovieActivityPackage.TrailerResponse;
import com.example.movies.MovieActivityPackage.TrailersAdapter;
import com.example.movies.MovieClass.Movie;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView title;
    private TextView year;
    private TextView description;
    private ImageView star;

    private MovieActivityViewModel viewModel;

    private RecyclerView recyclerView;
    private TrailersAdapter trailersAdapter;

    private RecyclerView recyclerViewComments;
    private CommentAdapter commentAdapter;

    private MovieDao movieDao;

    private final static String MOVIE_EXTRA = "extraMovie";
    private final static String TAG = "MovieActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        init();


        Movie movie = (Movie) getIntent().getSerializableExtra(MOVIE_EXTRA);
        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageView);
        title.setText(movie.getName());
        year.setText(String.valueOf(movie.getYear()));
        description.setText(movie.getDescription());

        trailers(movie);
        clickOnItem();

        comments(movie);

        checkTheStar(movie);
    }

    private void init() {
        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.title);
        year = findViewById(R.id.year);
        description = findViewById(R.id.description);
        star = findViewById(R.id.star);

        viewModel = new ViewModelProvider(this).get(MovieActivityViewModel.class);

        recyclerView = findViewById(R.id.recyclerViewTrailers);
        trailersAdapter = new TrailersAdapter();
        recyclerView.setAdapter(trailersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewComments = findViewById(R.id.recyclerViewComments);
        commentAdapter = new CommentAdapter();
        recyclerViewComments.setAdapter(commentAdapter);
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(this));
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieActivity.class);
        intent.putExtra(MOVIE_EXTRA, movie);
        return intent;
    }

    private void trailers(Movie movie) {
        int id = movie.getId();
        viewModel.loadTrailer(id);
        viewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
            }
        });
    }

    private void clickOnItem() {
        trailersAdapter.setOnClickTrailerListener(new TrailersAdapter.OnClickTrailerListener() {
            @Override
            public void OnClick(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()));
                startActivity(intent);
            }
        });
    }

    private void comments(Movie movie) {
        viewModel.loadComments(movie.getId());
        viewModel.getComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                commentAdapter.setComments(comments);
            }
        });
    }

    private void checkTheStar(Movie movie) {
        Drawable starOff = ContextCompat.getDrawable(MovieActivity.this, android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat.getDrawable(MovieActivity.this, android.R.drawable.star_big_on);

        viewModel.getFavoriteMovieVM(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDb) {
                if(movieFromDb == null) {
                    star.setBackground(starOff);
                    star.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.addFavoriteMovieVM(movie);
                            star.setBackground(starOn);
                        }
                    });
                } else {
                    star.setBackground(starOn);
                    star.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            viewModel.removeFavoriteMovieVM(movie.getId());
                            star.setBackground(starOff);
                        }
                    });
                }
            }
        });
    }


}