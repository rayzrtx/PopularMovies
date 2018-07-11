package com.example.android.popularmovies;

public class Review {

    private String movieReviewAuthor;
    private String movieReviewContent;

    public Review(){
    }

    public Review(String movieReviewAuthor, String movieReviewContent){
        this.movieReviewContent = movieReviewContent;
        this.movieReviewAuthor = movieReviewAuthor;
    }

    public String getMovieReviewAuthor() {
        return movieReviewAuthor;
    }

    public void setMovieReviewAuthor(String movieReviewAuthor) {
        this.movieReviewAuthor = movieReviewAuthor;
    }

    public String getMovieReviewContent() {
        return movieReviewContent;
    }

    public void setMovieReviewContent(String movieReviewContent) {
        this.movieReviewContent = movieReviewContent;
    }
}
