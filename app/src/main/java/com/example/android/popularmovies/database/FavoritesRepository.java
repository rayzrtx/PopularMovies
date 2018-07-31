package com.example.android.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.android.popularmovies.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository {

    private FavoritesDAO mFavoritesDAO;
    private LiveData<List<Movie>> mAllFavorites;

    FavoritesRepository(Application application){
        FavoritesDatabase database = FavoritesDatabase.getDatabase(application);
        mFavoritesDAO = database.favoritesDAO();
        mAllFavorites = mFavoritesDAO.loadAllFavorites();
    }

    LiveData<List<Movie>>loadAllFavorites(){
        return mAllFavorites;
    }


    //Insert must be done on a background thread
    public void insertFavorite(Movie favoriteMovie){
        new insertAsyncTask(mFavoritesDAO).execute(favoriteMovie);
    }

    //Delete must be done on a background thread
    public void deleteFavorite(Movie favoriteMovie){
        new deleteAsyncTask(mFavoritesDAO).execute(favoriteMovie);
    }

    //AsyncTask that handles inserting into database in background thread
    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void>{

        private FavoritesDAO mAsyncTaskDAO;

        insertAsyncTask(FavoritesDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            mAsyncTaskDAO.insertFavorite(movies[0]);
            return null;
        }
    }

    //AsyncTask to handle deleting favorites from database in background thread
    private static class deleteAsyncTask extends AsyncTask<Movie, Void, Void>{

        private FavoritesDAO mDeleteAsyncTaskDAO;

        deleteAsyncTask(FavoritesDAO dao){
            mDeleteAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            mDeleteAsyncTaskDAO.deleteFavorite(movies[0]);
            return null;
        }
    }
}
