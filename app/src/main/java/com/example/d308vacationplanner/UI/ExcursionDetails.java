package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.R;

import database.Repository;
import entities.Excursion;

public class ExcursionDetails extends AppCompatActivity {

    EditText editExcursionName;

    EditText editExcursionDetail;

    TextView vacationNameLabel;

    String excursionName;

    String vacationName;

    int excursionID;

    int vacationID;

    Repository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button saveExcursionButton = findViewById(R.id.saveExcursionButton);

        editExcursionName = findViewById(R.id.editExcursionName);
        editExcursionDetail = findViewById(R.id.editExcursionDetail);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        vacationName = getIntent().getStringExtra("vacationName");
        excursionID = getIntent().getIntExtra("excursionID", -1);
        excursionName = getIntent().getStringExtra("excursionName");
        editExcursionName.setText(excursionName);
        editExcursionDetail.setText(Integer.toString(excursionID));
        vacationNameLabel = findViewById(R.id.vacationNameLabel);
        vacationNameLabel.setText(vacationName);
        repository = new Repository(getApplication());
        saveExcursionButton.setOnClickListener(v -> {
            Excursion excursion;
            //creating a new excursion because we aren't editing an existing one.
            if(excursionID == -1){
                System.out.println("Creating a new excursion");
                //check if repo is empty and if it is then excursionID will be 1.
                if(repository.getmAllExcursions().isEmpty()){
                    System.out.println("The excursion repo is empty so we are using id 1");
                    excursionID = 1;
                    excursion = new Excursion(vacationID, excursionID, editExcursionName.getText().toString());
                    repository.insert(excursion);
                    finish();
                }else{
                    //if repo isn't empty we need the id of the last excursion in the list so we can set new one to that + 1.
                    excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                    excursion = new Excursion(vacationID, excursionID, editExcursionName.getText().toString());
                    repository.insert(excursion);
                    finish();
                }

                //update existing excursion
            }else{
                excursion = new Excursion(vacationID, excursionID, excursionName);
                repository.update(excursion);
                finish();
            }

        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saveExcursionMenuItem) {
            Excursion excursion;
            //creating a new excursion because we aren't editing an existing one.
            if (excursionID == -1) {
                System.out.println("Creating a new excursion");
                //check if repo is empty and if it is then excursionID will be 1.
                if (repository.getmAllExcursions().isEmpty()) {
                    System.out.println("The excursion repo is empty so we are using id 1");
                    excursionID = 1;
                    excursion = new Excursion(vacationID, excursionID, editExcursionName.getText().toString());
                    repository.insert(excursion);
                    this.finish();
                } else {
                    //if repo isn't empty we need the id of the last excursion in the list so we can set new one to that + 1.
                    excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                    excursion = new Excursion(vacationID, excursionID, editExcursionName.getText().toString());
                    repository.insert(excursion);
                    this.finish();
                }

                //update existing excursion
            } else {
                excursion = new Excursion(vacationID, excursionID, excursionName);
                repository.update(excursion);
                this.finish();
            }
        }
        if(item.getItemId() == R.id.deleteExcursionMenuItem){
            Excursion excursion = new Excursion(vacationID, excursionID, excursionName);
            repository.delete(excursion);
            this.finish();
        }
        return true;
    }

}