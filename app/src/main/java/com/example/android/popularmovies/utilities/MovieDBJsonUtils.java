package com.example.android.popularmovies.utilities;

import android.util.Log;

import com.example.android.popularmovies.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieDBJsonUtils {

    public static final String RESULTS_KEY = "results";
    public static final String TITLE_KEY = "title";
    public static final String POSTER_PATH_KEY = "poster_path";
    public static final String OVERVIEW_KEY = "overview";
    public static final String VOTE_AVG_KEY = "vote_average";
    public static final String RELEASE_DATE_KEY = "release_date";

    public static ArrayList<Movie> parseMovieJSON(String movieJSON){

        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(movieJSON);

            JSONArray resultsArray = rootObject.optJSONArray(RESULTS_KEY);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject resultsObject = resultsArray.getJSONObject(i);
                String movieTitle = resultsObject.getString(TITLE_KEY);
                String moviePosterPath = "http://image.tmdb.org/t/p/w185" + resultsObject.getString(POSTER_PATH_KEY);
                String movieOverview = resultsObject.getString(OVERVIEW_KEY);
                long movieVoteAvg = resultsObject.getLong(VOTE_AVG_KEY);
                String movieReleaseDate = resultsObject.getString(RELEASE_DATE_KEY);

                Movie newMovie = new Movie(movieTitle, movieReleaseDate, moviePosterPath, movieVoteAvg, movieOverview);

                movies.add(newMovie);
            }

        }catch (JSONException e){
            Log.e("JSON Utils", "Problem parsing the JSON results", e);
            return null;
        }
        return movies;
    }
}
