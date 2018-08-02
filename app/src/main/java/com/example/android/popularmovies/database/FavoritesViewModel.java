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


    private LiveData<List<Movie>> mAllFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        FavoritesDatabase favoritesDatabase = FavoritesDatabase.getDatabase(this.getApplication());
        mAllFavorites = favoritesDatabase.favoritesDAO().loadAllFavorites();


    }

    public LiveData<List<Movie>> loadAllFavorites(){
        return mAllFavorites;
    }


}
