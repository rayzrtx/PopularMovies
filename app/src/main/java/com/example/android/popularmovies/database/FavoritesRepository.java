package com.example.android.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.android.popularmovies.AppExecutors;
import com.example.android.popularmovies.Movie;

import java.util.ArrayList;
import java.util.List;

//All database logic handled through Repository
public class FavoritesRepository {

    private FavoritesDAO mFavoritesDAO;
    private LiveData<List<Movie>> mAllFavorites;

    FavoritesRepository(Application application){
        FavoritesDatabase database = FavoritesDatabase.getDatabase(application);
        mFavoritesDAO = database.favoritesDAO();
        mAllFavorites = mFavoritesDAO.loadAllFavorites();
    }

    //LiveData runs in background by default
    LiveData<List<Movie>>loadAllFavorites(){
        return mAllFavorites;
    }


    public void insertFavorite(final Movie favoriteMovie){
        //Insert must be done on a background thread
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mFavoritesDAO.insertFavorite(favoriteMovie);
            }
        });
    }

    public void deleteFavoriteById(final int id){
        //Delete must be done on a background thread
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mFavoritesDAO.deleteByFavoriteById(id);
            }
        });
    }

}
