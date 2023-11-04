package com.example.demo.Entities;

import jakarta.persistence.Entity;

@Entity
public class Artist extends Users {
    private long totalViews;

    public Artist(String username, String password) {
        super(username, password);
        this.totalViews = 0;
        setUserType(UserType.ARTIST);
    }

    public Artist() {
    }

    public long getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(long totalViews) {
        this.totalViews = totalViews;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "totalViews=" + totalViews +
                '}';
    }
}
