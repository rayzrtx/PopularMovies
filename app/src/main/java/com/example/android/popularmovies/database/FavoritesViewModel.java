package com.example.android.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.Movie;

import java.util.List;

//To provide data to the UI and to survive configuration changes
public class FavoritesViewModel extends AndroidViewModel {


    private LiveData<List<Movie>> mAllFavorites;
    private FavoritesRepository mFavoritesRepository;

    //Calls to database go through Repository
    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        mFavoritesRepository = new FavoritesRepository(application);
        mAllFavorites = mFavoritesRepository.loadAllFavorites();


    }

    public LiveData<List<Movie>> loadAllFavorites() {
        return mAllFavorites;
    }

    public void insertFavorite(Movie movie) {
        mFavoritesRepository.insertFavorite(movie);
    }

    public void deleteFavoriteById(int id) {
        mFavoritesRepository.deleteFavoriteById(id);
    }

}
