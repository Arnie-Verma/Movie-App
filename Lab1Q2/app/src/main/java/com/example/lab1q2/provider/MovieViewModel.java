package com.example.lab1q2.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {//if UI requests data it comes here
    //ViewModel provides data for a specific UI component such as an activity
    private MovieRepository repo;//instance of repository
    private LiveData<List<Movie>> movies;//instance of live data

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repo = new MovieRepository(application);
        movies = repo.getAllMovies();//uses repository to get all Customers
    }

    public LiveData<List<Movie>> getMovies(){
        return  movies;
    }

    public void insertNewMovie(Movie movie){
        repo.insertMovie(movie);
    }
    public void deleteAll(){ repo.deleteAll(); }
    public void deleteOldMovies(){ repo.deleteOld(); }
    public void deleteCostlyMovies(){ repo.deleteCostly(); }


}
