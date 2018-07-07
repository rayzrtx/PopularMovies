package com.example.android.popularmovies.data;

public class URLConstant {

    public static final String MOVIES_DB_BASE_URL = "https://api.themoviedb.org/3/movie/";

    //Base URL for Most Popular Movies
    public static final String MOST_POPULAR_BASE_URL = "popular?api_key=";

    //Base URL for Highest Rated Movies
    public static final String HIGHEST_RATED_BASE_URL = "top_rated?api_key=";

    //Personal API Key
    public static final String API_KEY = ""; //Please enter your own unique personal API key

    //Base URL for Movie Poster Image
    public static final String POSTER_PATH_BASE_URL = "http://image.tmdb.org/t/p/w185";

    //Base URL for Trailers of selected Movie
    public static final String MOVIE_TRAILER_BASE_URL = "/videos?api_key=";

    //Base URL for Reviews of selected Movie
    public static final String MOVIE_REVIEW_BASE_URL = "/reviews?api_key=";
}
