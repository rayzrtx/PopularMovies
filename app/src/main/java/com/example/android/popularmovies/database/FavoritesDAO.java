package com.example.android.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.popularmovies.Movie;

import java.util.ArrayList;
import java.util.List;

//This class is where all methods to access the database are defined
@Dao
public interface FavoritesDAO {

    //Command to load all movies in favorites table
    @Query("SELECT * FROM favorites_table")
    LiveData<List<Movie>> loadAllFavorites();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Movie favoriteMovie);

    @Delete
    void deleteFavorite(Movie favoriteMovie);

    //Query to delete movie from database with that movie ID
    @Query("DELETE FROM favorites_table WHERE movieID = :favoriteMovieId")
    void deleteByFavoriteById(int favoriteMovieId);

    //Query will return a List of Movies in Favorites DB that match the movie ID
    @Query("SELECT * FROM favorites_table WHERE movieID = :favoriteMovieID")
    List<Movie> checkForFavoriteMovie(int favoriteMovieID);

}
