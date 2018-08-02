package com.example.android.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.popularmovies.Movie;

import java.util.List;

public class QueryFavoritesViewModel extends ViewModel {

    private LiveData<List<Movie>> favoriteMovie;

    public QueryFavoritesViewModel(FavoritesDatabase database, int favoriteMovieID){
        favoriteMovie = database.favoritesDAO().checkForFavoriteMovie(favoriteMovieID);
    }

    public LiveData<List<Movie>> getFavoriteMovie() {
        return favoriteMovie;
    }




}
