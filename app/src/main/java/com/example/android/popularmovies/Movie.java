package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

//Parcelable interface will assist in storing and retrieving Movie data info to pass through Intent
public class Movie implements Parcelable {

    //Every Movie object will contain these five elements which will be parsed from JSON
    private String movieTitle;
    private String releaseDate;
    private String posterImageUrl;
    private Double voteAverage;
    private String synopsis;
    private int movieID;

    public Movie() {
    }

    public Movie(String movieTitle, String releaseDate, String posterImageUrl, Double voteAverage, String synopsis, int movieID) {
        this.movieTitle = movieTitle;
        this.releaseDate = releaseDate;
        this.posterImageUrl = posterImageUrl;
        this.voteAverage = voteAverage;
        this.synopsis = synopsis;
        this.movieID = movieID;
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

    public String getPosterImageUrl() {
        return posterImageUrl;
    }

    public void setPosterImageUrl(String posterImage) {
        this.posterImageUrl = posterImage;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getMovieID(){ return movieID; }

    public void setMovieID(int movieID) { this.movieID = movieID; }


    @Override
    public int describeContents() {
        return 0;
    }

    //write object values to parcel for storage
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieTitle);
        parcel.writeString(releaseDate);
        parcel.writeString(posterImageUrl);
        parcel.writeDouble(voteAverage);
        parcel.writeString(synopsis);
        parcel.writeInt(movieID);

    }

    //constructor that will be collecting values sent to receiving intent
    public Movie(Parcel parcel) {
        movieTitle = parcel.readString();
        releaseDate = parcel.readString();
        posterImageUrl = parcel.readString();
        voteAverage = parcel.readDouble();
        synopsis = parcel.readString();
        movieID = parcel.readInt();

    }

    //Will bind everything together when un-parceling the parcel and creating the Movie
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[0];
        }
    };

}
