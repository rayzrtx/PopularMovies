package com.example.android.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class FavoritesRepository {

    private FavoritesDAO mFavoritesDAO;
    private LiveData<List<Favorite>> mAllFavorites;

    FavoritesRepository(Application application){
        FavoritesDatabase database = FavoritesDatabase.getDatabase(application);
        mFavoritesDAO = database.favoritesDAO();
        mAllFavorites = mFavoritesDAO.loadAllFavorites();
    }

    LiveData<List<Favorite>> loadAllFavorites(){
        return mAllFavorites;
    }

    //Insert must be done on a background thread
    public void insert(Favorite favorite){
        new insertAsyncTask(mFavoritesDAO).execute(favorite);
    }

    //Delete must be done on a background thread
    public void delete(Favorite favorite){
        new deleteAsyncTask(mFavoritesDAO).execute(favorite);
    }

    //AsyncTask that handles inserting into database in background thread
    private static class insertAsyncTask extends AsyncTask<Favorite, Void, Void>{

        private FavoritesDAO mAsyncTaskDAO;

        insertAsyncTask(FavoritesDAO dao){
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            mAsyncTaskDAO.insertFavorite(favorites[0]);
            return null;
        }
    }

    //AsyncTask to handle deleting favorites from database in background thread
    private static class deleteAsyncTask extends AsyncTask<Favorite, Void, Void>{

        private FavoritesDAO mDeleteAsyncTaskDAO;

        deleteAsyncTask(FavoritesDAO dao){
            mDeleteAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            mDeleteAsyncTaskDAO.deleteFavorite(favorites[0]);
            return null;
        }
    }
}
