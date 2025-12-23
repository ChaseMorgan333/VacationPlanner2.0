package com.example.d308vacationplanner.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.R;

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import database.Repository;
import entities.Excursion;


public class ExcursionDetails extends AppCompatActivity {

    private static final String TAG = "ExcursionDetails";
    EditText editExcursionName;

    TextView editExcursionDate;

    TextView vacationNameLabel;

    String excursionName;

    String excursionDate;

    String vacationName;

    int excursionID;

    int vacationID;

    String vacationStartDate;

    String vacationEndDate;

    Button editExcursionDateButton;

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
        Log.d(TAG, "my on create has started");

        Button saveExcursionButton = findViewById(R.id.saveExcursionButton);
        editExcursionDateButton = findViewById(R.id.editExcursionDateButton);

        editExcursionName = findViewById(R.id.editExcursionName);
        editExcursionDate = findViewById(R.id.editExcursionDate);
        excursionDate = getIntent().getStringExtra("excursionDate");
        vacationID = getIntent().getIntExtra("vacationID", -1);
        Log.w(TAG, Integer.toString(vacationID));
        //vacationName = getIntent().getStringExtra("vacationName");
        excursionID = getIntent().getIntExtra("excursionID", -1);
        excursionName = getIntent().getStringExtra("excursionName");
        //vacationStartDate = getIntent().getStringExtra("vacationStartDate");
        //vacationEndDate = getIntent().getStringExtra("vacationEndDate");
        repository = new Repository(getApplication());
        editExcursionDate.setText(excursionDate);
        editExcursionName.setText(excursionName);
        editExcursionDate.setText(excursionDate);
        vacationNameLabel = findViewById(R.id.vacationNameLabel);


        vacationStartDate = repository.getmVacationStartDate(vacationID);
        vacationEndDate = repository.getmVacationEndDate(vacationID);
        vacationNameLabel.setText(repository.getmVacationName(vacationID));



        saveExcursionButton.setOnClickListener(v -> {
            if(hasDate()&&hasName()) {
                Excursion excursion;
                this.excursionName = editExcursionName.getText().toString();
                //creating a new excursion because we aren't editing an existing one.
                if (excursionID == -1) {
                    System.out.println("Creating a new excursion");
                    //check if repo is empty and if it is then excursionID will be 1.
                    if (repository.getmAllExcursions().isEmpty()) {
                        System.out.println("The excursion repo is empty so we are using id 1");
                        excursionID = 1;
                        excursion = new Excursion(vacationID, excursionID, editExcursionName.getText().toString(), editExcursionDate.getText().toString());
                        Toast.makeText(getApplicationContext(), "Excursion " + excursionName + " has been saved.", Toast.LENGTH_LONG).show();
                        repository.insert(excursion);
                        finish();
                    } else {
                        //if repo isn't empty we need the id of the last excursion in the list so we can set new one to that + 1.
                        excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                        excursion = new Excursion(vacationID, excursionID, editExcursionName.getText().toString(), editExcursionDate.getText().toString());
                        Toast.makeText(getApplicationContext(), "Excursion " + excursionName + " has been saved.", Toast.LENGTH_LONG).show();
                        repository.insert(excursion);
                        finish();
                    }

                    //update existing excursion
                } else {
                    excursion = new Excursion(vacationID, excursionID, excursionName, excursionDate);
                    Toast.makeText(getApplicationContext(), "Excursion " + excursionName + " has been updated.", Toast.LENGTH_LONG).show();
                    repository.update(excursion);
                    finish();
                }
            }

        });

        editExcursionDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });


    }
    private boolean hasName(){
        if (editExcursionName.getText().toString().isEmpty()||editExcursionName.getText().toString().isBlank()){
            Toast.makeText(this.getApplicationContext(), "Excursion must have a name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean hasDate(){
        if(editExcursionDate.getText().toString().isEmpty()||editExcursionDate.getText().toString().isBlank()){
            Toast.makeText(this.getApplicationContext(), "Excursion must have a date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.saveExcursionMenuItem) {
            if (hasName() && hasDate()) {
                Excursion excursion;
                this.excursionName = editExcursionName.getText().toString();
                //creating a new excursion because we aren't editing an existing one.
                if (excursionID == -1) {

                    //check if repo is empty and if it is then excursionID will be 1.
                    if (repository.getmAllExcursions().isEmpty()) {
                        excursionID = 1;
                        excursion = new Excursion(vacationID, excursionID, editExcursionName.getText().toString(), editExcursionDate.getText().toString());
                        System.out.println("inserting excursion");
                        repository.insert(excursion);
                        Toast.makeText(getApplicationContext(), "Excursion " + excursionName + " has been saved.", Toast.LENGTH_LONG).show();
                        this.finish();
                    } else {
                        //if repo isn't empty we need the id of the last excursion in the list so we can set new one to that + 1.
                        excursionID = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getExcursionID() + 1;
                        excursion = new Excursion(vacationID, excursionID, editExcursionName.getText().toString(), editExcursionDate.getText().toString());
                        System.out.println("inserting excursion2");
                        repository.insert(excursion);
                        Toast.makeText(getApplicationContext(), "Excursion " + excursionName + " has been saved.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }

                    //update existing excursion
                } else {
                    excursion = new Excursion(vacationID, excursionID, excursionName, excursionDate);
                    System.out.println("inserting excursion3");
                    repository.update(excursion);
                    Toast.makeText(getApplicationContext(), "Excursion " + excursionName + " has been updated.", Toast.LENGTH_LONG).show();
                    this.finish();
                }
            }
        }
            if (item.getItemId() == R.id.deleteExcursionMenuItem) {
                Excursion excursion = new Excursion(vacationID, excursionID, excursionName, excursionDate);
                repository.delete(excursion);
                this.finish();
            }
            if(item.getItemId() == R.id.notifyOnStartDay){
                if(!editExcursionDate.getText().toString().isEmpty()){
                    String excursionDate = editExcursionDate.getText().toString();

                    LocalDate excursionDateLocal = StartDatePickerFragment.parseDate(excursionDate);
                    try{
                        Date startDate = Date.from(excursionDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Long triggerStartDate = startDate.getTime();
                        Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                        intent.putExtra("key", "Excursion " + editExcursionName.getText().toString() + " is starting today");
                        PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_ONE_SHOT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerStartDate, sender);
                    } catch (Exception e) {

                    }
                }
            }
            return true;

    }

    private int clickedButtonID;
    private void showDatePickerDialog(View v){
        clickedButtonID = v.getId();
        if (v.getId() == R.id.editExcursionDateButton){
            ExcursionDatePickerFragment newFragment = new ExcursionDatePickerFragment(this);
            newFragment.show(getSupportFragmentManager(), "Date Picker" );
        }



    }

}