package com.example.android.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Favorite.class}, version = 1, exportSchema = false)
public abstract class FavoritesDatabase extends RoomDatabase {

    private static FavoritesDatabase sInstance;

    //Make database a singleton to prevent multiple instances of database opened at the same time
    public static FavoritesDatabase getDatabase(final Context context){
        if (sInstance == null){
            synchronized (FavoritesDatabase.class){
                if (sInstance == null){
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            FavoritesDatabase.class, "favorites_database").build();
                }
            }
        }
        return sInstance;
    }


    public abstract FavoritesDAO favoritesDAO();
}
