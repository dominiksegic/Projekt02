package com.example.projekt02;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class FigureViewModel extends AndroidViewModel {
    private FigureRepository repository;
    private LiveData<List<Figure>> allFigures;

    public FigureViewModel(@NonNull Application application) {
        super(application);
        repository = new FigureRepository(application);
        allFigures = repository.showAll();
    }
    public void insert(Figure figure){
        repository.insert(figure);
    }
    public void update(Figure figure){
        repository.update(figure);
    }
    public void delete(Figure figure){
        repository.delete(figure);
    }
    public void deleteAll(){
        repository.deleteAll();
    }
    public LiveData<List<Figure>> showAll(){
        return allFigures;
    }
}
