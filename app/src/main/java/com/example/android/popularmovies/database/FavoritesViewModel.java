package com.example.android.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.Movie;

import java.util.ArrayList;
import java.util.List;

//To provide data to the UI and to survive configuration changes
public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesRepository mRepository;
    private LiveData<List<Movie>> mAllFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new FavoritesRepository(application);
        mAllFavorites = mRepository.loadAllFavorites();
    }

    public LiveData<List<Movie>> loadAllFavorites(){
        return mAllFavorites;
    }


    public void insert(Movie favoriteMovie){
        mRepository.insertFavorite(favoriteMovie);
    }

    public void delete(Movie favoriteMovie){
        mRepository.deleteFavorite(favoriteMovie);
    }
}
