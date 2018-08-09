package com.example.android.popularmovies;

public class Trailer {

    private String movieTrailerKey;
    private String movieTrailerName;

    public Trailer() {
    }

    public Trailer(String movieTrailerKey, String movieTrailerName) {
        this.movieTrailerKey = movieTrailerKey;
        this.movieTrailerName = movieTrailerName;
    }

    public String getMovieTrailerKey() {
        return movieTrailerKey;
    }

    public void setMovieTrailerKey(String movieTrailerKey) {
        this.movieTrailerKey = movieTrailerKey;
    }

    public String getMovieTrailerName() {
        return movieTrailerName;
    }

    public void setMovieTrailerName(String movieTrailerName) {
        this.movieTrailerName = movieTrailerName;
    }
}
