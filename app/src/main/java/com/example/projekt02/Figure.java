package com.example.projekt02;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "figure_table")
public class Figure {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String date;
    private String series;
    private int rating;

    public Figure(String name, String date, String series, int rating) {
        this.name = name;
        this.date = date;
        this.series = series;
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getSeries() {
        return series;
    }

    public int getRating() {
        return rating;
    }
}
