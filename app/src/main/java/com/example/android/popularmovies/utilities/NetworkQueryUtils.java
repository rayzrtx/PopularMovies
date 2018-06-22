package com.example.android.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkQueryUtils {

    final static String MOVIE_DB_MOST_POPULAR_URL = "https://api.themoviedb.org/3/movie/popular?api_key=";
    final static String MOVIE_DB_HIGHEST_RATED_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=";
    final static String API_KEY = "";  //Please enter your own unique personal API key

    //This will build the URL for the most popular movies
    public static URL buildMostPopularUrl(){
        Uri movieDbQueryUri =
                Uri.parse(MOVIE_DB_MOST_POPULAR_URL + API_KEY);

        URL mostPopularURL = null;

        try {
           mostPopularURL  = new URL(movieDbQueryUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return mostPopularURL;
    }

    //This will build the URL for the highest rated movies
    public static URL buildHighestRatedUrl(){
        Uri movieDbQueryUri =
                Uri.parse(MOVIE_DB_HIGHEST_RATED_URL + API_KEY);

        URL highestRatedURL = null;

        try {
            highestRatedURL = new URL(movieDbQueryUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return highestRatedURL;
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
