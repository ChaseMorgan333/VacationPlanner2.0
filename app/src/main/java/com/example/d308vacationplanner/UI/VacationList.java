package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

import java.util.List;

import database.Repository;
import entities.Vacation;


public class VacationList extends AppCompatActivity {

    private Repository repository;
    private TextView noVacations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        repository = new Repository(getApplication());
        List<Vacation> allVacations = repository.getmAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
        noVacations = findViewById(R.id.noVacationsTextView);
        if(allVacations.isEmpty()){
            noVacations.setText("No vacations have been booked.");
        }else{
            noVacations.setText("");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.addVacation){
            Intent intent = new Intent(VacationList.this, VacationDetails.class);
            startActivity(intent);
            return true;
        }
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        List<Vacation> allVacations = repository.getmAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final VacationAdapter vacationAdapter = new VacationAdapter((this));
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
        if(allVacations.isEmpty()){
            noVacations.setText("No vacations have been booked.");
        }else{
            noVacations.setText("");
        }
    }
}