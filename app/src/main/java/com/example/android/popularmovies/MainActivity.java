package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MovieAdapter mAdapter;
    RecyclerView mMovieList;
    List<Movie> mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This is the recycler view where the list of movie posters will appear
        mMovieList = findViewById(R.id.movies_rv);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mMovieList.setLayoutManager(gridLayoutManager);

        mAdapter = new MovieAdapter(this, mMovie);
        mMovieList.setAdapter(mAdapter);

    }
}
