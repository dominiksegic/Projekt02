package com.example.projekt02;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Figure.class, version = 1)
public abstract class FigureDB extends RoomDatabase {

    private static FigureDB instance;

    public abstract FigureDAO figureDAO();

    public static synchronized FigureDB getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),FigureDB.class,"figure_database")
                    .fallbackToDestructiveMigration().addCallback(roomCallback).build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateAsyncTask(instance).execute();
        }
    };

    private static class PopulateAsyncTask extends AsyncTask <Void, Void, Void>{
        private FigureDAO figureDAO;

        private PopulateAsyncTask (FigureDB db){
            figureDAO = db.figureDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            figureDAO.insert(new Figure("Link", "28.9.1997", "Ocarina", 3));
            figureDAO.insert(new Figure("Zelda", "1.3.2001", "Twilight Princess", 2));
            figureDAO.insert(new Figure("Mario", "13.7.2012", "Super Mario Bros", 4));
            figureDAO.insert(new Figure("Link", "21.6.2011", "Twilight Princess", 5));
            return null;
        }
    }

}
