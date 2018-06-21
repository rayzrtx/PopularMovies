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
    final static String API_KEY = "";  //Please enter your own unique personal API key

    public static URL buildUrl(){
        Uri movieDbQueryUri =
                Uri.parse(MOVIE_DB_MOST_POPULAR_URL + API_KEY);

        URL url = null;

        try {
            url = new URL(movieDbQueryUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
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
