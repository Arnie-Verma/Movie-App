package com.example.lab1q2.provider;

public class FirebaseMovie {
    String titleFDB;
    String yearFDB;
    String countryFDB;
    String genreFDB;
    String costFDB;
    String keywordsFDB;


    public FirebaseMovie(String titleFDB, String yearFDB, String countryFDB, String genreFDB, String costFDB, String keywordsFDB) {
        this.titleFDB = titleFDB;
        this.yearFDB = yearFDB;
        this.countryFDB = countryFDB;
        this.genreFDB = genreFDB;
        this.costFDB = costFDB;
        this.keywordsFDB = keywordsFDB;
    }

    public String getTitleFDB() {
        return titleFDB;
    }

    public void setTitleFDB(String titleFDB) {
        this.titleFDB = titleFDB;
    }

    public String getYearFDB() {
        return yearFDB;
    }

    public void setYearFDB(String yearFDB) {
        this.yearFDB = yearFDB;
    }

    public String getCountryFDB() {
        return countryFDB;
    }

    public void setCountryFDB(String countryFDB) {
        this.countryFDB = countryFDB;
    }

    public String getGenreFDB() {
        return genreFDB;
    }

    public void setGenreFDB(String genreFDB) {
        this.genreFDB = genreFDB;
    }

    public String getCostFDB() {
        return costFDB;
    }

    public void setCostFDB(String costFDB) {
        this.costFDB = costFDB;
    }

    public String getKeywordsFDB() {
        return keywordsFDB;
    }

    public void setKeywordsFDB(String keywordsFDB) {
        this.keywordsFDB = keywordsFDB;
    }
}
