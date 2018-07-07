package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.popularmovies.utilities.MovieDBJsonUtils;
import com.example.android.popularmovies.utilities.MovieTrailerJsonUtils;
import com.example.android.popularmovies.utilities.NetworkQueryUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {

    //Movie Details variables
    TextView mMovieTitle;
    TextView mReleaseDate;
    TextView mRating;
    TextView mSynopsis;
    ImageView mMoviePoster;

    //Movie Trailer RecyclerView variables
    RecyclerView mMovieTrailerList;
    TextView mMovieTrailerTitle;
    ImageView mMovieTrailerImage;
    ProgressBar mLoadingSpinnerTrailer;
    TextView mErrorMessageTrailerTV;
    TextView mNoInternetTrailerTV;
    ArrayList<Trailer> mMovieTrailer;
    String mMovieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mLoadingSpinnerTrailer = findViewById(R.id.loading_spinner_trailer);
        mErrorMessageTrailerTV = findViewById(R.id.error_message_trailer_tv);
        mNoInternetTrailerTV = findViewById(R.id.no_internet_trailer_tv);

        //RecyclerView where Movie Trailers will appear
        mMovieTrailerList = findViewById(R.id.movie_trailer_rv);
        //Will remain invisible while data is retreived
        mMovieTrailerList.setVisibility(View.INVISIBLE);

        mMovieTrailer = new ArrayList<>();

        mMovieTrailerTitle = findViewById(R.id.movie_trailer_title_tv);
        mMovieTrailerImage = findViewById(R.id.movie_trailer_iv);

        //Set up button on action bar if not null
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        //If intent is not null then pass Movie data to activity
        if (intent != null){
            Movie movie = intent.getParcelableExtra("Movie");
            mMovieID = String.valueOf(movie.getMovieID());
            Log.i("MovieDetailsActivity", mMovieID);
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

    //Will return the URL to download movie trailer JSON
    private void makeMovieTrailerSearchQuery(String movieId){

    }

    //Makes the RecyclerView visible when data has been retrieved
    private void showMovieTrailerView(){
        mMovieTrailerList.setVisibility(View.VISIBLE);
        mLoadingSpinnerTrailer.setVisibility(View.INVISIBLE);
        mErrorMessageTrailerTV.setVisibility(View.INVISIBLE);
        mNoInternetTrailerTV.setVisibility(View.INVISIBLE);
    }

    //Shown when there was an issue retrieving trailer data
    private void showErrorMessageTrailerView(){
        mMovieTrailerList.setVisibility(View.INVISIBLE);
        mLoadingSpinnerTrailer.setVisibility(View.INVISIBLE);
        mErrorMessageTrailerTV.setVisibility(View.VISIBLE);
        mNoInternetTrailerTV.setVisibility(View.INVISIBLE);
    }

    private void showNoInternetMessageTrailerView(){
        mMovieTrailerList.setVisibility(View.INVISIBLE);
        mLoadingSpinnerTrailer.setVisibility(View.INVISIBLE);
        mErrorMessageTrailerTV.setVisibility(View.INVISIBLE);
        mNoInternetTrailerTV.setVisibility(View.VISIBLE);
    }

    //Checking if device is connected to the internet
    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        //checking if connected via mobile network or wifi and if so, obtain the type of connection
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            android.net.NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            //If mobile connection or wifi connection is connected or attempting to connect, return true for connected
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) {
                return true;
            } else
                return false;

        } else
            return false;
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

    public class MovieTrailerQueryTask extends AsyncTask<URL, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingSpinnerTrailer.setVisibility(View.VISIBLE);
        }

        //Will connect to URL and return Movie trailer JSON string info
        @Override
        protected String doInBackground(URL... urls) {

            URL searchURL = urls[0];

            String movieTrailerJSONResults = null;

            try {
                movieTrailerJSONResults = NetworkQueryUtils.getResponseFromHttpUrl(searchURL);
            }catch(IOException e){
                e.printStackTrace();
            }
            return movieTrailerJSONResults; //JSON string with all of the retrieved JSON data that now needs to be parsed
        }

        @Override
        protected void onPostExecute(String movieTrailerJSON) {
            mLoadingSpinnerTrailer.setVisibility(View.INVISIBLE);
            if (movieTrailerJSON != null && movieTrailerJSON != ""){
                //Set recyclerview to visible and make other views invisible
                showMovieTrailerView();
                //Will parse JSON data and return a list of movie objects
                mMovieTrailer = MovieTrailerJsonUtils.parseMovieTrailerJSON(movieTrailerJSON);

                //Bind parsed JSON data to recyclerview and use Adapter to populate UI


            }

        }
    }
}
