package com.example.d308vacationplanner;

import android.content.ClipData;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import dao.PacklistDAO;
import database.Repository;
import entities.Packlist;

public class PackingList extends AppCompatActivity {
    private int vacationID;
    private String vacationName;
    private TextView vacationNameLabel;
    private EditText editTextItemName;
    private Spinner categorySpinner;
    private CheckBox itemPackedCheckbox;
    private Button addItemButton;
    private SearchView searchView;
    private Button searchItemButton;
    private Spinner spinnerSearchCategory;
    private Button showCategoryButton;
    private TextView resultsForTextView;
    private TextView reportGeneratedTextView;

    private List<Packlist> itemsForTrip;

    private Repository repository;

    private Packlist currentPacklist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_packing_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        WindowCompat.setDecorFitsSystemWindows(getWindow(),true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vacationID = getIntent().getIntExtra("vacationID", -1);
        vacationName = getIntent().getStringExtra("vacationName");
        vacationNameLabel = findViewById(R.id.vacationNameLabelPackList2);
        vacationNameLabel.setText(vacationName);
        editTextItemName = findViewById(R.id.editTextItemName);
        categorySpinner = findViewById(R.id.categorySpinner);
        itemPackedCheckbox = findViewById(R.id.itemPackedCheckbox);
        addItemButton = findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
        repository = new Repository(getApplication());










    }//end of oncreate
    private void addNewItem(){
        //First thing is to set functionality for adding a new item to the database. All these things will happen when the add button is clicked.
        //We need to get the vacationID, theCategory, the itemName, packedYN, and the button to handle the save.
        String itemName = editTextItemName.getText().toString();
        String itemCategory = categorySpinner.getSelectedItem().toString();
        Boolean packedYN = itemPackedCheckbox.isChecked();
        //the currentPackList is an item we want to add to the database. First we need to make sure the item has a name and a category selected.
        //if the item name is empty or blank or null then "throw an error" as a toast. Otherwise proceed.
        if(itemName.isEmpty()||itemName.isBlank()){
            Toast.makeText(getApplicationContext(), "The item must have a name", Toast.LENGTH_SHORT).show();
        }else if (itemCategory.isEmpty()||itemCategory.isBlank()){
            Toast.makeText(getApplicationContext(), "The item must have a category", Toast.LENGTH_SHORT).show();
        }else{
            //if validation passes we will create the item.
            currentPacklist = new Packlist(vacationID, itemCategory, itemName, packedYN );
            repository.insertNewPacklistItem(currentPacklist);
            Toast.makeText(getApplicationContext(), "Item: " + itemName + " successfully added to category: " + itemCategory, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return true;
    }

}