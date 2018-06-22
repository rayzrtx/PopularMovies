package com.example.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.utilities.MovieDBJsonUtils;
import com.example.android.popularmovies.utilities.NetworkQueryUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickListener {

    MovieAdapter mAdapter;
    RecyclerView mMovieList;
    ArrayList<Movie> mMovie;
    Toast mToast;
    TextView mMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This is the recycler view where the list of movie posters will appear
        mMovieList = findViewById(R.id.movies_rv);

        mMovie = new ArrayList<>();

        mMovieTitle = findViewById(R.id.movie_title_tv);

        makeMovieDBSearchQuery();

    }

    //Will return the URL to download Movie DB json info for most popular movies
    void makeMovieDBSearchQuery(){
        URL builtURL = NetworkQueryUtils.buildMostPopularUrl();
        new MoviesDBQueryTask().execute(builtURL);
    }

    void makeHighestRatedMovieDBSearchQuery(){
        URL builtURL = NetworkQueryUtils.buildHighestRatedUrl();
        new MoviesDBQueryTask().execute(builtURL);
    }

    //AsyncTask to perform network request in background thread
    public class MoviesDBQueryTask extends AsyncTask<URL, Void, String>{


        //Will connect to URL and return JSON string info
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];

            String movieDBresults = null;
            try{
                movieDBresults = NetworkQueryUtils.getResponseFromHttpUrl(searchURL); //Retrieving JSON from URL
            }catch (IOException e){
                e.printStackTrace();
            }

            return movieDBresults; //JSON string with all of the retrieved JSON data that now needs to be parsed
        }

        //If JSON string is not empty or null, parse JSON string
        @Override
        protected void onPostExecute(String movieJSON) {
            if (movieJSON != null && movieJSON != "") {
                mMovie = MovieDBJsonUtils.parseMovieJSON(movieJSON); //Will parse JSON data and return a list of movie objects

                //Bind parsed JSON data to recyclerview and use Adapter to populate UI
                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                mMovieList.setLayoutManager(gridLayoutManager);
                mAdapter = new MovieAdapter(MainActivity.this, mMovie, MainActivity.this);
                mMovieList.setAdapter(mAdapter);

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
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()){
            case R.id.action_sort_item_most_popular:

                //Will return list of most popular movies
                makeMovieDBSearchQuery();

                return true;

            case R.id.action_sort_item_highest_rated:

                //Will return list of highest rated movies
                makeHighestRatedMovieDBSearchQuery();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //This is where you will create intent to open details about the movie that was clicked
    @Override
    public void onMovieItemClick(int clickedItemIndex){
        String toastMessage = "Item # " + clickedItemIndex + " clicked!";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT);
        mToast.show();

    }
}
