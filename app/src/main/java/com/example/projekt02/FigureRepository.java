package com.example.projekt02;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FigureRepository {

    private FigureDAO figureDAO;
    private LiveData<List<Figure>> allFigures;

    public FigureRepository(Application application) {
        FigureDB database = FigureDB.getInstance(application);
        figureDAO = database.figureDAO();
        allFigures = figureDAO.showAll();
    }
    public void insert (Figure figure){
        new InsertAsyncTask(figureDAO).execute(figure);
    }
    public void update (Figure figure){
        new UpdateAsyncTask(figureDAO).execute(figure);
    }
    public void delete (Figure figure){
        new DeleteAsyncTask(figureDAO).execute(figure);
    }
    public void deleteAll (){
        new DeleteAllAsyncTask(figureDAO).execute();
    }
    public LiveData<List<Figure>> showAll(){
        return allFigures;
    }
    private static class InsertAsyncTask extends AsyncTask <Figure, Void, Void>{
        private FigureDAO figureDAO;
        private InsertAsyncTask (FigureDAO figureDAO){
            this.figureDAO = figureDAO;
        }
        @Override
        protected Void doInBackground(Figure... figures) {
            figureDAO.insert(figures[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask <Figure, Void, Void>{
        private FigureDAO figureDAO;
        private UpdateAsyncTask (FigureDAO figureDAO){
            this.figureDAO = figureDAO;
        }
        @Override
        protected Void doInBackground(Figure... figures) {
            figureDAO.update(figures[0]);
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask <Figure, Void, Void>{
        private FigureDAO figureDAO;
        private DeleteAsyncTask (FigureDAO figureDAO){
            this.figureDAO = figureDAO;
        }
        @Override
        protected Void doInBackground(Figure... figures) {
            figureDAO.delete(figures[0]);
            return null;
        }
    }
    private static class DeleteAllAsyncTask extends AsyncTask <Void, Void, Void>{
        private FigureDAO figureDAO;
        private DeleteAllAsyncTask (FigureDAO figureDAO){
            this.figureDAO = figureDAO;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            figureDAO.deleteAll();
            return null;
        }
    }
}
