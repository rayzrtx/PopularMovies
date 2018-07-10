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

    //Base URL to retreive thumbnail of movie trailer
    public static final String TRAILER_THUMBNAIL_BASE_URL = "http://i3.ytimg.com/vi/";

    public static final String TRAILER_THUMBNAIL_URL_ENDING = "/hqdefault.jpg";

    //Base URL to open browser to selected movie trailer if YouTube app is not installed on device
    public static final String YOUTUBE_TRAILER_WEB_BASE_URL = "http://www.youtube.com/watch?v=";

    //Base URL to open YouTube app to selected movie trailer if app is installed on device
    public static final String YOUTUBE_APP_BASE_URL = "vnd.youtube:";
}
