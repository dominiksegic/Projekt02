package com.example.projekt02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditFigureActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.projekt02.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.projekt02.EXTRA_NAME";
    public static final String EXTRA_DATE = "com.example.projekt02.EXTRA_DATE";
    public static final String EXTRA_SERIES = "com.example.projekt02.EXTRA_SERIES";
    public static final String EXTRA_RATING = "com.example.projekt02.EXTRA_RATING";

    EditText editTextName;
    EditText editTextDate;
    EditText editTextSeries;
    NumberPicker numberPickerRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_figure);

        editTextName = findViewById(R.id.edit_text_name);
        editTextDate = findViewById(R.id.edit_text_date);
        editTextSeries = findViewById(R.id.edit_text_series);
        numberPickerRating = findViewById(R.id.number_picker_rating);
        numberPickerRating.setMinValue(0);
        numberPickerRating.setMaxValue(5);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ID)){
            setTitle("Edit Figure");

            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextDate.setText(intent.getStringExtra(EXTRA_DATE));
            editTextSeries.setText(intent.getStringExtra(EXTRA_SERIES));
            numberPickerRating.setValue(intent.getIntExtra(EXTRA_RATING,0));
        } else {
            setTitle("Add Figure");
        }
    }
    private void saveFigure(){

        String name = editTextName.getText().toString();
        String date = editTextDate.getText().toString();
        String series = editTextSeries.getText().toString();
        int rating = numberPickerRating.getValue();

        if(name.trim().isEmpty()||date.trim().isEmpty()||series.trim().isEmpty()){
            Toast.makeText(this, "Please fill the empty fields.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();

        data.putExtra(EXTRA_NAME,name);
        data.putExtra(EXTRA_DATE,date);
        data.putExtra(EXTRA_SERIES,series);
        data.putExtra(EXTRA_RATING,rating);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1){
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.save_note:
                saveFigure();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}