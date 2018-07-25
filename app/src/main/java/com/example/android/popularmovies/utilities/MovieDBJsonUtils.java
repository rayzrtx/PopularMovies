package com.example.android.popularmovies.utilities;

import android.util.Log;

import com.example.android.popularmovies.Movie;
import com.example.android.popularmovies.data.URLConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieDBJsonUtils {

    private static final String RESULTS_KEY = "results";
    private static final String TITLE_KEY = "title";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String OVERVIEW_KEY = "overview";
    private static final String VOTE_AVG_KEY = "vote_average";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String ID_KEY = "id";
    private static final String POSTER_IMAGE_BASE_URL = URLConstant.POSTER_PATH_BASE_URL;

    public static List<Movie> parseMovieJSON(String movieJSON) {

        List<Movie> movies = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(movieJSON);

            JSONArray resultsArray = rootObject.optJSONArray(RESULTS_KEY);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject resultsObject = resultsArray.getJSONObject(i);
                int movieID = resultsObject.getInt(ID_KEY);
                String movieTitle = resultsObject.getString(TITLE_KEY);
                String moviePosterPath = POSTER_IMAGE_BASE_URL + resultsObject.getString(POSTER_PATH_KEY);
                String movieOverview = resultsObject.getString(OVERVIEW_KEY);
                Double movieVoteAvg = resultsObject.getDouble(VOTE_AVG_KEY);
                String movieReleaseDate = resultsObject.getString(RELEASE_DATE_KEY);

                Movie newMovie = new Movie(movieTitle, movieReleaseDate, moviePosterPath, movieVoteAvg, movieOverview, movieID);

                movies.add(newMovie);
            }

        } catch (JSONException e) {
            Log.e("JSON Utils", "Problem parsing the JSON results", e);
            return null;
        }
        return movies;
    }
}
