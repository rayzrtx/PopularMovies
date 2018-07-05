package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView mMovieTitle;
    TextView mReleaseDate;
    TextView mRating;
    TextView mSynopsis;
    ImageView mMoviePoster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Set up button on action bar if not null
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        //If intent is not null then pass Movie data to activity
        if (intent != null){
            Movie movie = intent.getParcelableExtra("Movie");
            String movieID = String.valueOf(movie.getMovieID());
            Log.i("MovieDetailsActivity", movieID);
            //update UI of new activity with the Movie info
            updateUI(movie);
        }

    }

    //This method will populate the various views with the appropriate Movie data
    private void updateUI(Movie movie){

        mMovieTitle = findViewById(R.id.details_movie_title_tv);
        mReleaseDate = findViewById(R.id.details_movie_release_date_tv);
        mRating = findViewById(R.id.details_movie_vote_avg_tv);
        mSynopsis = findViewById(R.id.details_movie_synopsis_tv);
        mMoviePoster = findViewById(R.id.details_movie_poster_iv);

        String movieTitle = movie.getMovieTitle();
        String movieReleaseDate = movie.getReleaseDate();
        String movieReleaseYear = movieReleaseDate.substring(0, 4);
        String movieRating = String.valueOf(movie.getVoteAverage()) + "/10";
        String movieSynopsis = movie.getSynopsis();
        String moviePosterURL = movie.getPosterImageUrl();

        mMovieTitle.setText(movieTitle);
        mReleaseDate.setText(movieReleaseYear);
        mRating.setText(movieRating);
        mSynopsis.setText(movieSynopsis);

        Picasso.get()
                .load(moviePosterURL)
                .into(mMoviePoster);
    }

    //For implementing Up button functionality
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
