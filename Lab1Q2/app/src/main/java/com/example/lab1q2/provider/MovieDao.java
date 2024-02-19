package com.example.lab1q2.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {//class responsible for communicating with database
    @Query("SELECT * from movies") //defined database interactions
    LiveData<List<Movie>>  getAllMovies();//data holder class - communicates with view component and updates latest version of table

    @Insert
    void addMovie(Movie movie);

    @Query("delete FROM movies")
    void deleteAllMovies();

    @Query("delete from movies where movieYear < 2010")
    void deleteOldMovies();

    @Query("delete from movies where movieCost = (SELECT(MAX(movieCost)) FROM movies)")
    void deleteCostlyMovies();







}
