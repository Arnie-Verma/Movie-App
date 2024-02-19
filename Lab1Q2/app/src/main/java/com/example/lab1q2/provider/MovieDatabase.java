package com.example.lab1q2.provider;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public static final String DB_NAME = "MOVIE_DB_NAME";
    public abstract MovieDao movieDao();
    private static volatile MovieDatabase INSTANCE;
    // marking the instance as volatile to ensure atomic access to the variable
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService dbWriter= Executors.newFixedThreadPool(NUMBER_OF_THREADS); //manages asynchronous tasks

    public static MovieDatabase getMovieDB(Context context){//returns the instance

        if(INSTANCE==null){//is instance = to null
            synchronized (MovieDatabase.class){ //if not, synchronise so that only one thread can access the logic to avoid deadlock
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class,DB_NAME)//build a new instance if does not exist
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
