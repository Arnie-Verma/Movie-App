package com.example.lab1q2.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import static com.example.lab1q2.provider.Movie.MOVIE_NAME;

@Entity(tableName = MOVIE_NAME)
public class Movie {
    public static final String MOVIE_NAME = "movies";
    @PrimaryKey(autoGenerate = true) //ensures no duplication
    @NonNull
    @ColumnInfo(name = "movieID")
    private int id;
    @ColumnInfo(name = "movieName")
    private String movieD;
    @ColumnInfo(name = "movieYear")
    private String yearD;
    @ColumnInfo(name = "movieCountry")
    private String countryD;
    @ColumnInfo(name = "movieGenre")
    private String genreD;
    @ColumnInfo(name = "movieCost")
    private String costD;
    @ColumnInfo(name = "movieKeyword")
    private String keywordD;

    public Movie(@NonNull String movieD, String yearD, String countryD, String genreD, String costD, String keywordD) {
        this.movieD = movieD;
        this.yearD = yearD;
        this.countryD = countryD;
        this.genreD = genreD;
        this.costD = costD;
        this.keywordD = keywordD;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieD() {
        return movieD;
    }

    public String getYearD() {
        return yearD;
    }

    public String getCountryD() {
        return countryD;
    }

    public String getGenreD(){
        return genreD;
    }

    public String getCostD() {
        return costD;
    }

    public String getKeywordD() {
        return keywordD;
    }
}
