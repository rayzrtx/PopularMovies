package com.example.android.popularmovies.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "favorites_table")
public class Favorite {



    //This is the id of each entry into the table. It will be unique for each entry and will auto generate.
    @PrimaryKey(autoGenerate = true)
    private int id;

    //The id of the specific movie that was added to favorites
    private int movieID;

    //The title of the movie added to favorites
    private String movieTitle;

    //Constructor used when reading FROM database
    public Favorite(int id, int movieID, String movieTitle) {
        this.id = id;
        this.movieID = movieID;
        this.movieTitle = movieTitle;
    }

    //Constructor used when reading TO database
    @Ignore
    public Favorite(int movieID, String movieTitle) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
}
