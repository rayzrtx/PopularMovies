package com.example.android.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.database.FavoritesDatabase;
import com.example.android.popularmovies.database.FavoritesViewModel;
import com.example.android.popularmovies.utilities.MovieDBJsonUtils;
import com.example.android.popularmovies.utilities.NetworkQueryUtils;
import com.facebook.stetho.Stetho;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickListener {

    MovieAdapter mAdapter;
    RecyclerView mMovieList;
    List<Movie> mMovie;
    TextView mMovieTitle;
    TextView mErrorMessageTextView;
    ProgressBar mProgressBar;
    TextView mNoInternetTextView;

    FavoritesDatabase mDatabase;
    GridLayoutManager gridLayoutManager;

    final String MOVIE_CATEGORY_KEY = "movie_category";
    final String POPULAR = "most_popular";
    final String TOP_RATED = "top_rated";
    final String FAVORITES = "favorites";
    String currentMovieSelection;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mErrorMessageTextView = findViewById(R.id.error_message_tv);
        mProgressBar = findViewById(R.id.loading_spinner);
        mNoInternetTextView = findViewById(R.id.no_internet_tv);


        //This is the recycler view where the list of movie posters will appear
        mMovieList = findViewById(R.id.movies_rv);

        //Recyclerview is invisible until data is retreived
        mMovieList.setVisibility(View.INVISIBLE);

        mMovie = new ArrayList<>();

        mMovieTitle = findViewById(R.id.movie_title_tv);

        mDatabase = FavoritesDatabase.getDatabase(getApplicationContext());

        //Selected menu item
        currentMovieSelection = getMenuSelection();

        //Load proper list depending on which menu item was selected
        switch (currentMovieSelection) {
            case POPULAR:
                makeMovieDBSearchQuery();
                break;
            case TOP_RATED:
                makeHighestRatedMovieDBSearchQuery();
                break;
            case FAVORITES:
                mMovieList.setVisibility(View.VISIBLE);
                bindDataToRecyclerview();
                getFavoriteMovies();
        }


        //If there is no internet connection, show No Internet message.
        if (!isConnected(MainActivity.this)) {

            showNoInternetMessage();

        }

    }

    //Will return the default Most Popular list upon initial load. Otherwise it will return the selected menu item.
    public String getMenuSelection() {
        sharedPreferences = this.getSharedPreferences("Selection", Context.MODE_PRIVATE);
        String sortBy = sharedPreferences.getString(MOVIE_CATEGORY_KEY, POPULAR);
        return sortBy;
    }

    public void saveMenuSelection(String newMenuSelection) {
        sharedPreferences = this.getSharedPreferences("Selection", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MOVIE_CATEGORY_KEY, newMenuSelection);
        editor.apply();
    }

    //Will return the URL to download Movie DB json info for most popular movies
    void makeMovieDBSearchQuery() {
        URL builtURL = NetworkQueryUtils.buildMostPopularUrl();
        new MoviesDBQueryTask().execute(builtURL);
    }

    void makeHighestRatedMovieDBSearchQuery() {
        URL builtURL = NetworkQueryUtils.buildHighestRatedUrl();
        new MoviesDBQueryTask().execute(builtURL);
    }

    //Will load all favorite movies in database and observers will be notified when there are changes to favorites DB
    void getFavoriteMovies() {
        FavoritesViewModel favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);
        favoritesViewModel.loadAllFavorites().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                mMovie = movies;
                mAdapter.setFavorites(mMovie);
            }
        });

    }

    //RecyclerView becomes visible when movie data has been retrieved
    private void showMovieDataView() {
        mMovieList.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mNoInternetTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    //Shown when there was an issue retrieving movie data
    private void showErrorMessage() {
        mMovieList.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
        mNoInternetTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
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

    //Shown when there is no internet connection detected
    private void showNoInternetMessage() {
        mMovieList.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        mNoInternetTextView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    //Bind data to recyclerview and use Adapter to populate UI
    private void bindDataToRecyclerview() {
        gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mMovieList.setLayoutManager(gridLayoutManager);
        mAdapter = new MovieAdapter(MainActivity.this, mMovie, MainActivity.this);
        mMovieList.setAdapter(mAdapter);
    }


    //AsyncTask to perform network request in background thread
    public class MoviesDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }


        //Will connect to URL and return JSON string info
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];

            String movieDBresults = null;
            try {
                movieDBresults = NetworkQueryUtils.getResponseFromHttpUrl(searchURL); //Retrieving JSON from URL
            } catch (IOException e) {
                e.printStackTrace();
            }

            return movieDBresults; //JSON string with all of the retrieved JSON data that now needs to be parsed
        }

        //If JSON string is not empty or null, parse JSON string
        @Override
        protected void onPostExecute(String movieJSON) {
            mProgressBar.setVisibility(View.INVISIBLE);
            if (movieJSON != null && movieJSON != "") {
                //Set recyclerview to visible
                showMovieDataView();
                mMovie = MovieDBJsonUtils.parseMovieJSON(movieJSON); //Will parse JSON data and return a list of movie objects

                //Bind parsed JSON data to recyclerview and use Adapter to populate UI
                bindDataToRecyclerview();

            } else {
                showErrorMessage();
            }
        }
    }

    //To create Overflow Menu for sorting movie list
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/sort_menu.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.sort_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // User clicked on a menu option in the app bar overflow menu
        switch (id) {
            case R.id.action_sort_item_most_popular:
                saveMenuSelection(POPULAR);
                //Will return list of most popular movies
                makeMovieDBSearchQuery();

                return true;

            case R.id.action_sort_item_highest_rated:
                saveMenuSelection(TOP_RATED);
                //Will return list of highest rated movies
                makeHighestRatedMovieDBSearchQuery();

                return true;

            case R.id.action_sort_item_favorites:
                saveMenuSelection(FAVORITES);
                //Will return list of movies that have been added as a favorite
                getFavoriteMovies();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //MovieDetailsActivity will open with details of the movie that is clicked. Movie info is passed through intent.
    @Override
    public void onMovieItemClick(int clickedItemIndex) {
        Intent movieDetailIntent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        movieDetailIntent.putExtra("Movie", mMovie.get(clickedItemIndex));
        startActivity(movieDetailIntent);
    }
}
