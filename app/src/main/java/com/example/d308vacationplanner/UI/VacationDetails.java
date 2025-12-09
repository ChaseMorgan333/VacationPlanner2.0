package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.List;

import database.Repository;
import entities.Excursion;
import entities.Vacation;

public class VacationDetails extends AppCompatActivity {

    EditText editName;


    EditText editID;

    String name;



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

        editName = findViewById(R.id.editVacation);
        name = getIntent().getStringExtra("name");
        editName.setText(name);
        vacationID = getIntent().getIntExtra("id", -1);
        editID = findViewById(R.id.editVacation2);
        editID.setText(Integer.toString(vacationID));


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                //  startActivity(intent);
            }
        });
        /*
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        final VacationAdapter partAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(partAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredParts = new ArrayList<>();
        for(Excursion excursion : repository.getmAllExcursions()){
            if(excursion.getVacationID() == vacationID) filteredParts.add(excursion);
        }
        excursionAdapter.setExcursions(filteredExcursions);
         */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if save button selected
        repository = new Repository(getApplication());
        if (item.getItemId() == R.id.saveVacationMenuItem) {
            Vacation vacation;
            //creating a new vacation because vacationID -1 indicates that no vacationID was passed in the intent (we aren't editing an existing vacation)
            if(vacationID == -1){
                //creating a new vacation
                if(repository.getmAllVacations().size()==0){
                    vacationID = 1;
                    vacation = new Vacation(vacationID, editName.getText().toString());
                    repository.insert(vacation);
                    this.finish();
                }else{
                    //if the repo is not empty we get the id of the last vacation in the list and set the new vacation id to that number
                    //plus 1
                    vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                    vacation = new Vacation(vacationID, editName.getText().toString());
                    Toast.makeText(getApplicationContext(), "New vacation created: " + vacation.getVacationName(), Toast.LENGTH_LONG).show();
                    repository.insert(vacation);
                    this.finish();
                }
                //updating an existing vacation
            }else{
                vacation = new Vacation(vacationID, editName.getText().toString());
                repository.update(vacation);
                this.finish();
            }
            return true;
        }

        if (item.getItemId() == R.id.deleteVacationMenuItem) {

            Toast.makeText(getApplicationContext(), "You are deleting vacation with ID: " + vacationID, Toast.LENGTH_LONG).show();
            Vacation testVacation = new Vacation(vacationID, "test");
            repository.delete(testVacation);
            this.finish();
            return true;
        }


        return true;

    }

}