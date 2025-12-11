package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.ArrayList;
import java.util.List;

import database.Repository;
import entities.Excursion;
import entities.Vacation;

public class VacationDetails extends AppCompatActivity {

    EditText editName;
    String name;


    EditText editAccommodation;
    String accommodation;

    EditText editVacationStartDate;
    TextView vacationStartDate;
    String startDate;

    EditText editVacationEndDate;
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
        editVacationStartDate = findViewById(R.id.editVacationStartDate);
        startDate = getIntent().getStringExtra("startDate");
        editVacationStartDate.setText(startDate);
        editVacationEndDate = findViewById(R.id.editVacationEndDate);
        endDate = getIntent().getStringExtra("endDate");
        editVacationEndDate.setText(endDate);



        fab.setOnClickListener(new View.OnClickListener() {
            //this button is to add new excursions, to edit existing ones you click them in the recycler view.
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                intent.putExtra("vacationName", name);
                startActivity(intent);
            }
        });
       reloadRecyclerView();



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
                    vacation = new Vacation(vacationID, editName.getText().toString(),editAccommodation.getText().toString(),vacationStartDate.getText().toString(),vacationEndDate.getText().toString());
                    repository.insert(vacation);
                    this.finish();
                }else{
                    //if the repo is not empty we get the id of the last vacation in the list and set the new vacation id to that number
                    //plus 1
                    vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                    vacation = new Vacation(vacationID, editName.getText().toString(),editAccommodation.getText().toString(),vacationStartDate.getText().toString(),vacationEndDate.getText().toString());
                    Toast.makeText(getApplicationContext(), "New vacation created: " + vacation.getVacationName(), Toast.LENGTH_LONG).show();
                    repository.insert(vacation);
                    this.finish();
                }
                //updating an existing vacation
            }else{
                vacation = new Vacation(vacationID, editName.getText().toString(),editAccommodation.getText().toString(),vacationStartDate.getText().toString(),vacationEndDate.getText().toString());
                repository.update(vacation);
                this.finish();
            }
            return true;
        }

        if (item.getItemId() == R.id.deleteVacationMenuItem) {



            if(repository.getmAssociatedExcursions(vacationID).size()!=0){
               Toast.makeText(getApplicationContext(), "Vacation has associated excursions, and cannot be deleted.", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Vacation deleted.", Toast.LENGTH_LONG).show();
                repository.delete(vacationID);
            }
            this.finish();
            return true;
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

}