package com.example.android.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.URLConstant;
import com.example.android.popularmovies.database.FavoritesDAO;
import com.example.android.popularmovies.database.FavoritesDatabase;
import com.example.android.popularmovies.database.FavoritesViewModel;
import com.example.android.popularmovies.database.QueryFavoritesViewModel;
import com.example.android.popularmovies.database.QueryFavoritesViewModelFactory;
import com.example.android.popularmovies.utilities.MovieDBJsonUtils;
import com.example.android.popularmovies.utilities.MovieReviewJsonUtils;
import com.example.android.popularmovies.utilities.MovieTrailerJsonUtils;
import com.example.android.popularmovies.utilities.NetworkQueryUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity implements TrailerAdapter.TrailerItemClickListener {

    //Movie Details variables
    TextView mMovieTitle;
    TextView mReleaseDate;
    TextView mRating;
    TextView mSynopsis;
    ImageView mMoviePoster;
    //The movie that was clicked
    Movie mMovie;

    //Movie Trailer RecyclerView variables
    RecyclerView mMovieTrailerList;
    TextView mMovieTrailerTitle;
    ImageView mMovieTrailerImage;
    ProgressBar mLoadingSpinnerTrailer;
    TextView mErrorMessageTrailerTV;
    ArrayList<Trailer> mMovieTrailer;
    String mMovieID;
    TrailerAdapter mAdapter;

    //Movie Review RecyclerView variables
    RecyclerView mMovieReviewRV;
    TextView mMovieReviewAuthorName;
    TextView mMovieReviewContent;
    ProgressBar mLoadingSpinnerReview;
    TextView mErrorMessageReviewTV;
    ArrayList<Review> mMovieReviewList;
    ReviewAdapter mReviewAdapter;
    TextView mNoReviewsMessageTV;

    ImageView mHeartIcon;
    TextView mFavoritesTextView;
    FavoritesDatabase mDatabase;

    int numberOfMoviesInDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //For trailer RecyclerView
        mLoadingSpinnerTrailer = findViewById(R.id.loading_spinner_trailer);
        mErrorMessageTrailerTV = findViewById(R.id.error_message_trailer_tv);

        //RecyclerView where Movie Trailers will appear
        mMovieTrailerList = findViewById(R.id.movie_trailer_rv);
        //Will remain invisible while data is retreived
        mMovieTrailerList.setVisibility(View.INVISIBLE);

        mMovieTrailer = new ArrayList<>();

        mMovieTrailerTitle = findViewById(R.id.movie_trailer_title_tv);
        mMovieTrailerImage = findViewById(R.id.movie_trailer_iv);

        //For Review RecyclerView
        mLoadingSpinnerReview = findViewById(R.id.loading_spinner_reviews);
        mErrorMessageReviewTV = findViewById(R.id.error_message_reviews_tv);
        mNoReviewsMessageTV = findViewById(R.id.empty_review_message_tv);

        //Recyclerview where Reviews will appear
        mMovieReviewRV = findViewById(R.id.movie_review_rv);
        //Will remain invisible while data is retreived
        mMovieReviewRV.setVisibility(View.INVISIBLE);

        mMovieReviewList = new ArrayList<>();

        mMovieReviewAuthorName = findViewById(R.id.movie_review_author_name_tv);
        mMovieReviewContent = findViewById(R.id.movie_review_content_tv);

        mHeartIcon = findViewById(R.id.favorites_icon);
        mFavoritesTextView = findViewById(R.id.favorites_text);
        mDatabase = FavoritesDatabase.getDatabase(getApplicationContext());


        //Set up button on action bar if not null
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        //If intent is not null then pass Movie data to activity
        if (intent != null) {
            mMovie = intent.getParcelableExtra("Movie");
            mMovieID = String.valueOf(mMovie.getMovieID());
            //update UI of new activity with the Movie info
            updateUI(mMovie);
            //Use the ID of the movie clicked to retreive Trailer info
            makeMovieTrailerSearchQuery(mMovieID);
            makeMovieReviewSearchQuery(mMovieID);
        }

        //Querying DB by returning List of Movies in Favorites DB with that movie ID
        queryIfFavorite(mMovie);


        mHeartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If Movie is in Favorite database then delete it and update the favorites icon
                if (isFavoriteMovie()) {
                    removeFromFavorites();

                } else {
                    addToFavorites();
                }
            }
        });

    }

    //Add movie to favorites database
    private void addToFavorites() {
        FavoritesViewModel favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        favoritesViewModel.insertFavorite(mMovie);
        Toast.makeText(MovieDetailsActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
    }

    //Remove movie from favorites database
    private void removeFromFavorites() {
        int id = mMovie.getMovieID();
        FavoritesViewModel favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        favoritesViewModel.deleteFavoriteById(id);
        Toast.makeText(MovieDetailsActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
    }

    //If there is at least one movie in Favorites DB with movie ID then movie is a favorite
    boolean isFavoriteMovie() {
        return numberOfMoviesInDB > 0;
    }

    //Querying DB by returning List of Movies in Favorites DB with that movie ID
    private void queryIfFavorite(final Movie favoriteMovie) {
        int id = favoriteMovie.getMovieID();
        QueryFavoritesViewModelFactory factory = new QueryFavoritesViewModelFactory(mDatabase, id);
        QueryFavoritesViewModel viewModel = ViewModelProviders.of(MovieDetailsActivity.this, factory).get(QueryFavoritesViewModel.class);
        //Set observer on the movie to see if it is added as a favorite or removed from favorites and update UI in real time
        viewModel.getFavoriteMovie().observe(MovieDetailsActivity.this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                numberOfMoviesInDB = movies.size();
                //If size of List returned is greater than 0, then movie is a favorite so heart icon will be red
                if (numberOfMoviesInDB > 0) {
                    mHeartIcon.setImageResource(R.drawable.ic_favorite_red_heart_24dp);
                    mFavoritesTextView.setText(R.string.remove_from_favorites);
                } else {
                    //Movie is not a favorite so heart will be clear
                    mHeartIcon.setImageResource(R.drawable.ic_favorite_clear_heart_24dp);
                    mFavoritesTextView.setText(R.string.add_to_favorites);
                }
            }
        });

    }


    //This method will populate the various views with the appropriate Movie data
    private void updateUI(Movie movie) {

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
    private void makeMovieTrailerSearchQuery(String movieId) {
        URL builtURL = NetworkQueryUtils.buildMovieTrailerUrl(movieId);
        new MovieTrailerQueryTask().execute(builtURL);
    }

    //Will return the URL to download movie review JSON
    private void makeMovieReviewSearchQuery(String movieID) {
        URL builtURL = NetworkQueryUtils.buildMovieReviewUrl(movieID);
        new MovieReviewQueryTask().execute(builtURL);
    }


    //Makes the Trailer RecyclerView visible when data has been retrieved
    private void showMovieTrailerView() {
        mMovieTrailerList.setVisibility(View.VISIBLE);
        mLoadingSpinnerTrailer.setVisibility(View.INVISIBLE);
        mErrorMessageTrailerTV.setVisibility(View.INVISIBLE);
    }

    //Makes the Review RecyclerView visible when data has been retrieved
    private void showMovieReviewView() {
        mMovieReviewRV.setVisibility(View.VISIBLE);
        mLoadingSpinnerReview.setVisibility(View.INVISIBLE);
        mErrorMessageReviewTV.setVisibility(View.INVISIBLE);
        mNoReviewsMessageTV.setVisibility(View.INVISIBLE);
    }

    //Shown when there was an issue retrieving trailer data
    private void showErrorMessageTrailerView() {
        mMovieTrailerList.setVisibility(View.INVISIBLE);
        mLoadingSpinnerTrailer.setVisibility(View.INVISIBLE);
        mErrorMessageTrailerTV.setVisibility(View.VISIBLE);
    }

    //Shown when there was an issue retrieving review data
    private void showErrorMessageReviewView() {
        mMovieReviewRV.setVisibility(View.INVISIBLE);
        mLoadingSpinnerReview.setVisibility(View.INVISIBLE);
        mErrorMessageReviewTV.setVisibility(View.VISIBLE);
        mNoReviewsMessageTV.setVisibility(View.INVISIBLE);
    }

    //Shown when there are no reviews to show for a movie
    private void showNoReviewsView() {
        mMovieReviewRV.setVisibility(View.INVISIBLE);
        mLoadingSpinnerReview.setVisibility(View.INVISIBLE);
        mErrorMessageReviewTV.setVisibility(View.INVISIBLE);
        mNoReviewsMessageTV.setVisibility(View.VISIBLE);
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

    //When a trailer item is clicked, create intent to view selected youtube trailer
    @Override
    public void onTrailerItemClick(int clickedItemIndex) {

        //Extract key of the clicked movie trailer
        String videoKey = mMovieTrailer.get(clickedItemIndex).getMovieTrailerKey();

        Context context = getApplicationContext();

        //Intent will play selected trailer using the YouTube app (if installed on device)
        Intent youTubeWebIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLConstant.YOUTUBE_TRAILER_WEB_BASE_URL + videoKey));

        //Intent will play selected trailer on YouTube website using a browser if YouTube app is not installed
        Intent youTubeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URLConstant.YOUTUBE_APP_BASE_URL + videoKey));

        //Try to use YouTube app to view selected trailer
        try {
            context.startActivity(youTubeAppIntent);
        } catch (ActivityNotFoundException e) {
            //If YouTube app is not installed, view in browser
            context.startActivity(youTubeWebIntent);
        }

    }

    //AsyncTask for retreiving Trailer info
    public class MovieTrailerQueryTask extends AsyncTask<URL, Void, String> {

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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieTrailerJSONResults; //JSON string with all of the retrieved JSON data that now needs to be parsed
        }

        //If JSON string is not empty or null, parse trailer JSON string
        @Override
        protected void onPostExecute(String movieTrailerJSON) {
            mLoadingSpinnerTrailer.setVisibility(View.INVISIBLE);
            if (movieTrailerJSON != null && movieTrailerJSON != "") {
                //Set recyclerview to visible and make other views invisible
                showMovieTrailerView();
                //Will parse JSON data and return a list of trailer objects
                mMovieTrailer = MovieTrailerJsonUtils.parseMovieTrailerJSON(movieTrailerJSON);

                //Bind parsed JSON data to recyclerview and use Adapter to populate UI
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MovieDetailsActivity.this);
                mMovieTrailerList.setLayoutManager(linearLayoutManager);
                mMovieTrailerList.setNestedScrollingEnabled(false);
                mAdapter = new TrailerAdapter(MovieDetailsActivity.this, mMovieTrailer, MovieDetailsActivity.this);
                //Set trailer adapter on trailer recyclerview
                mMovieTrailerList.setAdapter(mAdapter);

            } else {
                showErrorMessageTrailerView();
            }

        }
    }

    //AsyncTask for retreiving movie review data
    public class MovieReviewQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingSpinnerReview.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {

            URL searchURL = urls[0];

            String movieReviewJSONResults = null;

            try {
                movieReviewJSONResults = NetworkQueryUtils.getResponseFromHttpUrl(searchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Review JSON string that now needs to be parsed
            return movieReviewJSONResults;
        }

        @Override
        protected void onPostExecute(String movieReviewJSON) {
            mLoadingSpinnerReview.setVisibility(View.INVISIBLE);

            if (movieReviewJSON != null && movieReviewJSON != "") {
                //Set recyclerview to visible and make other views invisible
                showMovieReviewView();
                //Will parse JSON data and return a list of review objects
                mMovieReviewList = MovieReviewJsonUtils.parseMovieReviewJSON(movieReviewJSON);

                //If list of reviews is not null, get size of list
                if (mMovieReviewList != null) {
                    int reviewListSize = mMovieReviewList.size();
                    //If size of list is 0, then show "No reviews" message
                    if (reviewListSize == 0) {
                        showNoReviewsView();
                        //Otherwise, if list is not null or empty, then pass the list to the adapter
                    } else {
                        //Bind parsed JSON data to recyclerview and use Adapter to populate UI
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MovieDetailsActivity.this);
                        mMovieReviewRV.setLayoutManager(layoutManager);
                        mMovieReviewRV.setNestedScrollingEnabled(false);
                        mReviewAdapter = new ReviewAdapter(MovieDetailsActivity.this, mMovieReviewList);
                        //Set Review adapter on review recyclerview
                        mMovieReviewRV.setAdapter(mReviewAdapter);
                    }
                }
                //If JSON is null or empty then show error message
            } else {
                showErrorMessageReviewView();
            }
        }
    }

}
