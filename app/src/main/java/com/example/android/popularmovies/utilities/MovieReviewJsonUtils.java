package com.example.android.popularmovies.utilities;

import android.util.Log;

import com.example.android.popularmovies.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieReviewJsonUtils {
    private static final String RESULTS_KEY = "results";
    private static final String AUTHOR_KEY = "author";
    private static final String CONTENT_KEY = "content";

    public static ArrayList<Review> parseMovieReviewJSON(String movieReviewJson){

        ArrayList<Review> reviews = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(movieReviewJson);

            JSONArray resultsArray = rootObject.optJSONArray(RESULTS_KEY);

            for (int i = 0; i < resultsArray.length(); i++){
                JSONObject resultsObject = resultsArray.getJSONObject(i);
                String author = resultsObject.getString(AUTHOR_KEY);
                String content = resultsObject.getString(CONTENT_KEY);

                Review newReview = new Review(author, content);

                reviews.add(newReview);
            }

        }catch (JSONException e){
            Log.e("MovieReviewJsonUtils", "Problem parsing the JSON results", e);
            return null;
        }
        return reviews;
    }
}
