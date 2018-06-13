package com.example.android.popularmovies;

public class Movie {

    //Every Movie object will contain these five elements which will be parsed from JSON
    private String movieTitle;
    private String releaseDate;
    private String posterImage;
    private float voteAverage;
    private String synopsis;

    public Movie(){
    }

    public Movie(String movieTitle, String releaseDate, String posterImage, float voteAverage, String synopsis) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.posterImage = posterImage;
        this.voteAverage = voteAverage;
        this.synopsis = synopsis;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
