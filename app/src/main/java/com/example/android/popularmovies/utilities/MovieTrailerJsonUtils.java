package com.example.android.popularmovies.utilities;

import android.util.Log;

import com.example.android.popularmovies.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieTrailerJsonUtils {

    private static final String KEY_KEY = "key";
    private static final String NAME_KEY = "name";
    private static final String RESULTS_KEY = "results";

    public static ArrayList<Trailer> parseMovieTrailerJSON(String movieTrailerJSON){

        ArrayList<Trailer> trailers = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(movieTrailerJSON);

            JSONArray resultsArray = rootObject.optJSONArray(RESULTS_KEY);

            for (int i = 0; i < resultsArray.length(); i++){
                JSONObject resultsObject = resultsArray.getJSONObject(i);
                String key = resultsObject.getString(KEY_KEY);
                String name = resultsObject.getString(NAME_KEY);

                Trailer newTrailer = new Trailer(key, name);

                trailers.add(newTrailer);
            }



        }catch (JSONException e){
            Log.e("MovieTrailerJSONUtils", "Problem parsing the JSON results", e);
            return null;
        }
        return trailers;
    }
}
