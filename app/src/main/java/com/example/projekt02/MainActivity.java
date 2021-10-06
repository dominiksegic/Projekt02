package com.example.projekt02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_NOTE_REQUEST = 1;
    private static final int EDIT_NOTE_REQUEST = 2;
    FigureViewModel figureViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAddFigure = findViewById(R.id.addButton);
        btnAddFigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditFigureActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final FigureAdapter figureAdapter = new FigureAdapter();
        recyclerView.setAdapter(figureAdapter);
        figureViewModel = new ViewModelProvider(this).get(FigureViewModel.class);
        figureViewModel.showAll().observe(this, new Observer<List<Figure>>() {
            @Override
            public void onChanged(List<Figure> figures) {
                figureAdapter.setFigureList(figures);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            figureViewModel.delete(figureAdapter.getFigureAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Figure deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        figureAdapter.setOnItemClickListener(new FigureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Figure figure) {
                Intent intent = new Intent(MainActivity.this, AddEditFigureActivity.class);
                intent.putExtra(AddEditFigureActivity.EXTRA_ID,figure.getId());
                intent.putExtra(AddEditFigureActivity.EXTRA_NAME,figure.getName());
                intent.putExtra(AddEditFigureActivity.EXTRA_DATE,figure.getDate());
                intent.putExtra(AddEditFigureActivity.EXTRA_SERIES,figure.getSeries());
                intent.putExtra(AddEditFigureActivity.EXTRA_RATING,figure.getRating());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String name = data.getStringExtra(AddEditFigureActivity.EXTRA_NAME);
            String date = data.getStringExtra(AddEditFigureActivity.EXTRA_DATE);
            String series = data.getStringExtra(AddEditFigureActivity.EXTRA_SERIES);
            int rating = data.getIntExtra(AddEditFigureActivity.EXTRA_RATING, 0);

            Figure figure = new Figure(name, date, series, rating);
            figureViewModel.insert(figure);
            Toast.makeText(this, "Figure Saved", Toast.LENGTH_SHORT).show();
        } else if(requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditFigureActivity.EXTRA_ID, -1);
            if (id == -1){
                Toast.makeText(this, "Figure can't be edited", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddEditFigureActivity.EXTRA_NAME);
            String date = data.getStringExtra(AddEditFigureActivity.EXTRA_DATE);
            String series = data.getStringExtra(AddEditFigureActivity.EXTRA_SERIES);
            int rating = data.getIntExtra(AddEditFigureActivity.EXTRA_RATING, 0);

            Figure figure = new Figure(name, date, series, rating);
            figure.setId(id);
            figureViewModel.update(figure);

            Toast.makeText(this, "Figure Edited", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Figure Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_all_figures:
                figureViewModel.deleteAll();
                Toast.makeText(this, "Deleted all figures", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}