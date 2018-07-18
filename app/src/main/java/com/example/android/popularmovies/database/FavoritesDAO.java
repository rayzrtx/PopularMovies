package com.example.android.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

//This class is where all methods to access the database are defined
@Dao
public interface FavoritesDAO {

    //Command to load all movies in favorites table
    @Query("SELECT * FROM favorites_table")
    LiveData<List<Favorite>> loadAllFavorites();

    @Insert
    void insertFavorite(Favorite favorite);

    @Delete
    void deleteFavorite(Favorite favorite);

}
