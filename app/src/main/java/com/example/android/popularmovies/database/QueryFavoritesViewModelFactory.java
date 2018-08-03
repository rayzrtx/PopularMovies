package com.example.android.popularmovies.database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

//Class created so that favorite movie ID value can be passed to the ViewModel
public class QueryFavoritesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final FavoritesDatabase mFavoritesDatabase;
    private final int mFavoriteMovieID;


    public QueryFavoritesViewModelFactory(FavoritesDatabase favoritesDatabase, int favoriteMovieID) {
        mFavoritesDatabase = favoritesDatabase;
        mFavoriteMovieID = favoriteMovieID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new QueryFavoritesViewModel(mFavoritesDatabase, mFavoriteMovieID);
    }
}
