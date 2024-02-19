package com.example.lab1q2;

public class MovieDetails {
    private String titleDetails, yearDetails, countryDetails, genreDetails, costDetails, keywordDetails, costUSDDetails;

    public MovieDetails( String titleDetails, String yearDetails, String countryDetails, String genreDetails, String costDetails, String keywordDetails, String costUSDDetails){
        this.titleDetails=titleDetails;
        this.yearDetails=yearDetails;
        this.countryDetails=countryDetails;
        this.genreDetails=genreDetails;
        this.costDetails=costDetails;
        this.keywordDetails=keywordDetails;
        this.costUSDDetails = costUSDDetails;
    }

    public String getTitleDetails(){
        return titleDetails;
    }
    public String getYearDetails(){
        return yearDetails;
    }
    public String getCountryDetails(){
        return countryDetails;
    }
    public String getGenreDetails(){
        return genreDetails;
    }
    public String getCostDetails(){
        return costDetails;
    }
    public String getKeywordDetails(){
        return keywordDetails;
    }
    public String getCostUSDDetails(){return costUSDDetails;}



}


