package com.example.projekt02;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FigureDAO {

    @Insert
    void insert (Figure figure);
    @Update
    void update (Figure figure);
    @Delete
    void delete (Figure figure);

    @Query("DELETE FROM figure_table")
    void deleteAll();

    @Query("SELECT * FROM figure_table ORDER BY id DESC")
    LiveData<List<Figure>> showAll();
}
