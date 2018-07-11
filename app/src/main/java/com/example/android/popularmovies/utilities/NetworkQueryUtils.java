package com.example.android.popularmovies.utilities;

import android.net.Uri;

import com.example.android.popularmovies.data.URLConstant;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkQueryUtils {

    private final static String MOVIE_DB_MOST_POPULAR_URL = URLConstant.MOVIES_DB_BASE_URL + URLConstant.MOST_POPULAR_BASE_URL;
    private final static String MOVIE_DB_HIGHEST_RATED_URL = URLConstant.MOVIES_DB_BASE_URL + URLConstant.HIGHEST_RATED_BASE_URL;
    private final static String API_KEY = URLConstant.API_KEY;  //Please enter your own unique personal API key

    //This will build the URL for the most popular movies
    public static URL buildMostPopularUrl() {
        Uri movieDbQueryUri =
                Uri.parse(MOVIE_DB_MOST_POPULAR_URL + API_KEY);

        URL mostPopularURL = null;

        try {
            mostPopularURL = new URL(movieDbQueryUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mostPopularURL;
    }

    //This will build the URL for the highest rated movies
    public static URL buildHighestRatedUrl() {
        Uri movieDbQueryUri =
                Uri.parse(MOVIE_DB_HIGHEST_RATED_URL + API_KEY);

        URL highestRatedURL = null;

        try {
            highestRatedURL = new URL(movieDbQueryUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return highestRatedURL;
    }

    //This will build the URL for movie trailers
    public static URL buildMovieTrailerUrl(String movieID){
        Uri movieTrailerQueryUri =
                Uri.parse(URLConstant.MOVIES_DB_BASE_URL + movieID + URLConstant.MOVIE_TRAILER_BASE_URL + API_KEY);

        URL movieTrailerURL = null;
        try {
            movieTrailerURL = new URL(movieTrailerQueryUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return movieTrailerURL;
    }

    //This will build the URL for the movie reviews
    public static URL buildMovieReviewUrl(String movieID){
        Uri movieReviewQueryUri =
                Uri.parse(URLConstant.MOVIES_DB_BASE_URL + movieID + URLConstant.MOVIE_REVIEW_BASE_URL + API_KEY);

        URL movieReviewURL = null;
        try {
            movieReviewURL = new URL(movieReviewQueryUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return movieReviewURL;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
