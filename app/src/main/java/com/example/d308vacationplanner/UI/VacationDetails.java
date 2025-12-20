package com.example.d308vacationplanner.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.Repository;
import entities.Excursion;
import entities.Vacation;

public class VacationDetails extends AppCompatActivity{

    EditText editName;
    String name;


    EditText editAccommodation;
    String accommodation;

    Button editVacationStartDate;
    TextView vacationStartDate;
    String startDate;

    Button editVacationEndDate;
    TextView vacationEndDate;
    String endDate;







    int vacationID;

    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        editName = findViewById(R.id.editVacationName);
        name = getIntent().getStringExtra("name");
        editName.setText(name);
        vacationID = getIntent().getIntExtra("id", -1);
        editAccommodation = findViewById(R.id.editAccommodation);
        accommodation = getIntent().getStringExtra("accommodation");
        editAccommodation.setText(accommodation);
        vacationStartDate = findViewById(R.id.vacationStartDate);
        startDate = getIntent().getStringExtra("startDate");
        vacationStartDate.setText(startDate);
        vacationEndDate = findViewById(R.id.vacationEndDate);
        endDate = getIntent().getStringExtra("endDate");
        vacationEndDate.setText(endDate);
        editVacationStartDate = findViewById(R.id.editStartDateButton);
        editVacationStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(hasExcursions()){
                    Toast.makeText(getApplicationContext(),"All excursions must be deleted before editing start date.", Toast.LENGTH_LONG).show();
                }else {
                    showDatePickerDialog(v);
                }
            }
        });
        editVacationEndDate = findViewById(R.id.editEndDateButton);
        editVacationEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasExcursions()){
                    Toast.makeText(getApplicationContext(),"All excursions must be deleted before editing end date.", Toast.LENGTH_LONG).show();
                }else {
                    showDatePickerDialog(v);
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            //this button is to add new excursions, to edit existing ones you click them in the recycler view.
            @Override
            public void onClick(View v) {
                if(hasAccommodation()&&hasName()&&hasStartDate()&&hasEndDate()) {
                    saveVacation();
                    Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                    intent.putExtra("vacationID", vacationID);
                    intent.putExtra("vacationName", name);
                    intent.putExtra("vacationStartDate", startDate);
                    intent.putExtra("vacationEndDate", endDate);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "All vacation details must be entered first.", Toast.LENGTH_LONG).show();
                }
            }
        });
       reloadRecyclerView();



    }
    private boolean hasExcursions(){
        if(!repository.getmAssociatedExcursions(vacationID).isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    //This method allows code reuse inside onCreate and onResume so recycler view can refresh.
    public void reloadRecyclerView(){
        //This is the recyclerView that shows the associated excursions
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        //instantiating the repo and passing getApplication for the context
        repository = new Repository(getApplication());
        //This is the adapter that adapts the excursion list item to the recycler view.
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //this list is going to contain the excursions related to the selected vacation.
        List<Excursion> filteredExcursions = new ArrayList<>();
        for(Excursion excursion : repository.getmAllExcursions()){
            if(excursion.getVacationID() == vacationID) filteredExcursions.add(excursion);

        }
        excursionAdapter.setExcursions(filteredExcursions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }
    //Below are the boolean methods for checking that the vacation has a start date, end date, name, and accommodation.
    private boolean hasStartDate(){
       if(this.vacationStartDate.getText().toString().isEmpty()){
           Toast.makeText(this.getApplicationContext(), "Vacation must have a start date", Toast.LENGTH_LONG).show();
           return false;
       }else {
           return true;
       }
    }
    private boolean hasEndDate() {
        if(this.vacationEndDate.getText().toString().isEmpty()){
            Toast.makeText(this.getApplicationContext(), "Vacation must have an end date", Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean hasName(){
        if(this.editName.getText().toString().isEmpty()||this.editName.getText().toString().isBlank()){
            Toast.makeText(this.getApplicationContext(),"Vacation must have a name", Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean hasAccommodation(){
        if(this.editAccommodation.getText().toString().isEmpty()||this.editAccommodation.getText().toString().isBlank()){
            Toast.makeText(this.getApplicationContext(),"Vacation must have an accommodation.", Toast.LENGTH_LONG).show();
            return false;
        }else {
            return true;
        }
    }

    private void saveVacation(){
        Vacation vacation;
        if(hasStartDate()&&hasEndDate()&&hasName()&&hasAccommodation()) {
            //creating a new vacation because vacationID -1 indicates that no vacationID was passed in the intent (we aren't editing an existing vacation)
            if (vacationID == -1) {
                //creating a new vacation
                if (repository.getmAllVacations().size() == 0) {
                    vacationID = 1;

                    vacation = new Vacation(vacationID, editName.getText().toString(), editAccommodation.getText().toString(), vacationStartDate.getText().toString(), vacationEndDate.getText().toString());
                    this.name = editName.getText().toString();
                    this.accommodation = editAccommodation.getText().toString();
                    this.startDate = vacationStartDate.getText().toString();
                    this.endDate = vacationEndDate.getText().toString();
                    repository.insert(vacation);


                } else {
                    //if the repo is not empty we get the id of the last vacation in the list and set the new vacation id to that number
                    //plus 1
                    vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;

                    vacation = new Vacation(vacationID, editName.getText().toString(), editAccommodation.getText().toString(), vacationStartDate.getText().toString(), vacationEndDate.getText().toString());
                    this.name = editName.getText().toString();
                    this.accommodation = editAccommodation.getText().toString();
                    this.startDate = vacationStartDate.getText().toString();
                    this.endDate = vacationEndDate.getText().toString();
                    Toast.makeText(getApplicationContext(), "New vacation created: " + vacation.getVacationName(), Toast.LENGTH_LONG).show();
                    repository.insert(vacation);


                }
                //updating an existing vacation
            } else {

                vacation = new Vacation(vacationID, editName.getText().toString(), editAccommodation.getText().toString(), vacationStartDate.getText().toString(), vacationEndDate.getText().toString());
                this.name = editName.getText().toString();
                this.accommodation = editAccommodation.getText().toString();
                this.startDate = vacationStartDate.getText().toString();
                this.endDate = vacationEndDate.getText().toString();
                repository.update(vacation);


            }
            Toast.makeText(this.getApplicationContext(), "Vacation: " + editName.getText().toString() + " has been saved.", Toast.LENGTH_LONG).show();
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if save button selected
        //repository = new Repository(getApplication());
        if (item.getItemId() == R.id.saveVacationMenuItem) {
            saveVacation();
            return true;
        }

        if (item.getItemId() == R.id.deleteVacationMenuItem) {



            if(repository.getmAssociatedExcursions(vacationID).size()!=0){
               Toast.makeText(getApplicationContext(), "Vacation has associated excursions, and cannot be deleted.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Vacation: " + name + " deleted.", Toast.LENGTH_LONG).show();
                repository.delete(vacationID);
                this.finish();
            }

            return true;
        }

        if(item.getItemId() == R.id.notifyMenuItem){
            if(hasStartDate()&&hasEndDate()&&hasName()&&hasAccommodation()) {

                String startDateFromScreen = vacationStartDate.getText().toString();
                String endDateFromScreen = vacationEndDate.getText().toString();

                //try catch
                LocalDate myStartDateLocal = StartDatePickerFragment.parseDate(startDateFromScreen);
                LocalDate myEndDateLocal = StartDatePickerFragment.parseDate(endDateFromScreen);
                if (myStartDateLocal.isEqual(myEndDateLocal)) {
                    try {
                        Date startDate = Date.from(myStartDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Long triggerStartDate = startDate.getTime();
                        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                        intent.putExtra("key", "Vacation " + name + " is starting and ending today.");
                        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerStartDate, sender);
                    } catch (Exception e) {

                    }
                    Toast.makeText(this.getApplicationContext(), "Notification set for: " + editName.getText().toString(), Toast.LENGTH_LONG).show();
                } else {
                    try {
                        Date startDate = Date.from(myStartDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Long triggerStartDate = startDate.getTime();
                        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                        intent.putExtra("key", "Vacation " + name + " is starting.");
                        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerStartDate, sender);
                    } catch (Exception e) {

                    }

                    try {
                        Date endDate = Date.from(myEndDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        Long triggerEndDate = endDate.getTime();
                        Intent intent = new Intent(VacationDetails.this, MyReceiver.class);
                        intent.putExtra("key", "Vacation " + name + " is ending.");
                        PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_ONE_SHOT);
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerEndDate, sender);
                    } catch (Exception e) {

                    }
                    Toast.makeText(this.getApplicationContext(), "Notification set for: " + editName.getText().toString(), Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this.getApplicationContext(), "All vacation details must be entered first.", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if(item.getItemId()==R.id.shareVacationMenuItem){
            //Make sure none of the vacation details are empty before sharing.
            if(this.hasName()&&this.hasStartDate()&&this.hasEndDate()&&this.hasAccommodation()){
            Intent shareVacationIntent = new Intent();
            shareVacationIntent.setAction(Intent.ACTION_SEND);
            shareVacationIntent.putExtra(Intent.EXTRA_TITLE, "Vacation: " + editName.getText().toString());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Vacation Name: " + editName.getText().toString() + "\n");
            stringBuilder.append("Accommodation: " + editAccommodation.getText().toString() + "\n");
            stringBuilder.append("Start Date: " + startDate + "\n");
            stringBuilder.append("End Date: " + endDate + "\n");
            String vacationDetails = stringBuilder.toString();
            shareVacationIntent.putExtra(Intent.EXTRA_TEXT, vacationDetails);
            shareVacationIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(shareVacationIntent, "Sharing");
            startActivity(shareIntent);
            return true;
            }else{
                Toast.makeText(this.getApplicationContext(), "All vacation details must be entered first.", Toast.LENGTH_LONG).show();
            }
        }
        if(item.getItemId()==R.id.addExcursionMenuItem){
            if(hasName()&&hasStartDate()&&hasEndDate()&&hasAccommodation()){
                saveVacation();
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                intent.putExtra("vacationName", name);
                intent.putExtra("vacationStartDate", startDate);
                intent.putExtra("vacationEndDate", endDate);
                startActivity(intent);
            }

        }else{
            Toast.makeText(this.getApplicationContext(), "All vacation details must be entered first.", Toast.LENGTH_LONG).show();
        }
        if(item.getItemId()==R.id.deleteAllExcursionsMenuItem){
            List<Excursion> associatedExcursions = repository.getmAssociatedExcursions(vacationID);
            for(Excursion excursion : associatedExcursions){
                repository.delete(excursion);

            }
            reloadRecyclerView();
        }


        return true;

    }
    @Override
    protected void onResume(){
        super.onResume();
        reloadRecyclerView();
        /*List<Excursion> associatedExcursions = repository.getmAssociatedExcursions(vacationID);
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter((this));
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(associatedExcursions);*/

    }



    private int clickedButtonID;
    private void showDatePickerDialog(View v){
        clickedButtonID = v.getId();
        if (v.getId() == R.id.editStartDateButton){
            StartDatePickerFragment newFragment = new StartDatePickerFragment(this);
            newFragment.show(getSupportFragmentManager(), "Date Picker" );
        }
        if (v.getId() == R.id.editEndDateButton) {

            EndDatePickerFragment newFragment = new EndDatePickerFragment(this);
            newFragment.show(getSupportFragmentManager(), "Date Picker");
        }


    }


}