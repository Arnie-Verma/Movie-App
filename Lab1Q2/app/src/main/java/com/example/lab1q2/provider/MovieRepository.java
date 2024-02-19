package com.example.lab1q2.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {//communicate with viewmodel (live data) and of the DAO
    //avoids the need for the UI controller and ViewModel to contain code that directly accesses sources
    private MovieDao movieDao;
    private LiveData<List<Movie>> allMovies;//object of live data
    MovieDatabase db;

    public MovieRepository(Application application){//single source of truth for all app data holds latest data, communicates with view model
        db=MovieDatabase.getMovieDB(application); // getsDatabase
        movieDao=db.movieDao();//use customer DAO object to return all movies
        allMovies=db.movieDao().getAllMovies();
    }

    //fetch from DB

    public LiveData<List<Movie>> getAllMovies() {
        return allMovies;
    }
    //returns all customers no need for permission of dataWriteExecutor

    void insertMovie(Movie movie){

        MovieDatabase.dbWriter.execute(()->{movieDao.addMovie(movie);});
    }

    void deleteAll(){
        MovieDatabase.dbWriter.execute(()->{
            movieDao.deleteAllMovies();
        });
    }

    void deleteOld(){
        MovieDatabase.dbWriter.execute(()->{
            movieDao.deleteOldMovies();
        });
    }

    void deleteCostly(){
        MovieDatabase.dbWriter.execute(()->{
            movieDao.deleteCostlyMovies();
        });
    }
}
