package com.example.demo.DTO;

public class ArtistChart {
    private String artistName;
    private long totalViews;


    public ArtistChart(String artistName, long totalViews) {
        this.artistName = artistName;
        this.totalViews = totalViews;
    }

    public ArtistChart(){

    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public long getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(long totalViews) {
        this.totalViews = totalViews;
    }
}
