package com.example.android.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

//To provide data to the UI and to survive configuration changes
public class FavoritesViewModel extends AndroidViewModel {

    private FavoritesRepository mRepository;
    private LiveData<List<Favorite>> mAllFavorites;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        mRepository = new FavoritesRepository(application);
        mAllFavorites = mRepository.loadAllFavorites();
    }

    LiveData<List<Favorite>> loadAllFavorites(){
        return mAllFavorites;
    }

    public void insert(Favorite favorite){
        mRepository.insert(favorite);
    }

    public void delete(Favorite favorite){
        mRepository.delete(favorite);
    }
}
