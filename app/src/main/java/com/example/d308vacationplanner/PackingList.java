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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.UI.PacklistAdapter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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

    private Button showAllButton;

    private Button showPackedButton;
    private Button showUnapckedButton;


    private static List<Packlist> itemsToDisplay;


    private Repository repository;

    private Packlist currentPacklist;

    // Current Date and Time (with time zone)
    private ZonedDateTime currentZonedDateTime; //ZonedDateTime.now();

    private LocalDateTime dateTimeLocal; //LocalDateTime.now()


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
        resultsForTextView = findViewById(R.id.resultsForTextView);
        reportGeneratedTextView = findViewById(R.id.reportGeneratedTextView);
        showPackedButton = findViewById(R.id.showPackedButton);

        showUnapckedButton = findViewById(R.id.showUnpackedButton);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewItem();
            }
        });
        repository = new Repository(getApplication());
        spinnerSearchCategory = findViewById(R.id.spinnerSearchCategory);

        showCategoryButton = findViewById(R.id.showCategoryButton);
        showCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryToSearch = spinnerSearchCategory.getSelectedItem().toString();
                searchItemByCategory(categoryToSearch);
                Toast.makeText(getApplicationContext(), "Searching by category: " + categoryToSearch, Toast.LENGTH_SHORT).show();
            }
        });

        showAllItems();
        showPackedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //repository method that runs dao query to get all the packed items
                showPackedItems();
            }
        });

        showUnapckedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUnPackedItems();
            }
        });

        //Need to format the datetime so it fits in the view.
        //reportGeneratedTextView = findViewById(R.id.reportGeneratedTextView);
        //reportGeneratedTextView.setText(ZonedDateTime.now().toString());
        searchView = findViewById(R.id.searchView);
        searchItemButton = findViewById(R.id.searchItemButton);
        searchItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchToken = searchView.getQuery().toString();
                if(searchToken.isBlank()||searchToken.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter a query.", Toast.LENGTH_SHORT).show();
                }else{
                    searchItemByName(searchToken);
                }

            }
        });

        showAllButton = findViewById(R.id.showallbutton);
        showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllItems();
            }
        });






    }//end of oncreate
    public static void refreshRecyclerView(List<Packlist> newList){
        itemsToDisplay = newList;

    }


    private String formattedDateTime(LocalDateTime dateTime){
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        return dateTime.format(customFormatter);
    }

    private void showAllItems(){
        RecyclerView recyclerView = findViewById(R.id.packlistrecyclerview);
        List<Packlist> allItems = repository.getAllItems(vacationID);
        PacklistAdapter packlistAdapter = new PacklistAdapter(this);
        recyclerView.setAdapter(packlistAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        packlistAdapter.setmPacklists(allItems);
        resultsForTextView.setText("All Items");
        dateTimeLocal = LocalDateTime.now();
        reportGeneratedTextView.setText(formattedDateTime(dateTimeLocal));
        packlistAdapter.notifyDataSetChanged();
    }

    private void showPackedItems(){
        //get the recyclerview
        RecyclerView recyclerView = findViewById(R.id.packlistrecyclerview);
        //create a list of items to display
        itemsToDisplay = repository.getPackedItems(vacationID);
        //create the adapter
        final PacklistAdapter adapter = new PacklistAdapter(this);
        //set the adapter to the recyclerview
        recyclerView.setAdapter(adapter);
        //set the layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set the list of items to the adapter
        adapter.setmPacklists(itemsToDisplay);
        resultsForTextView.setText("All Packed Items");
        adapter.notifyDataSetChanged();
    }

    private void showUnPackedItems(){
        //get the recyclerview
        RecyclerView recyclerView = findViewById(R.id.packlistrecyclerview);
        //create a list of items to display
        itemsToDisplay = repository.getGetUnpackedItems(vacationID);
        //create the adapter
        final PacklistAdapter adapter = new PacklistAdapter(this);
        //set the adapter to the recyclerview
        recyclerView.setAdapter(adapter);
        //set the layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set the list of items to the adapter
        adapter.setmPacklists(itemsToDisplay);
        resultsForTextView.setText("All Unpacked Items");
        adapter.notifyDataSetChanged();
    }

    private void searchItemByCategory(String category){
        //get the recyclerview
        RecyclerView recyclerView = findViewById(R.id.packlistrecyclerview);
        //create a list of items to display
        itemsToDisplay = repository.getPacklistItemByCategory(category, vacationID);
        //create the adapter
        final PacklistAdapter adapter = new PacklistAdapter(this);
        //set the adapter to the recyclerview
        recyclerView.setAdapter(adapter);
        //set the layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set the list of items to the adapter
        adapter.setmPacklists(itemsToDisplay);
        resultsForTextView.setText("Category: " + category);
        adapter.notifyDataSetChanged();


    }

    private void searchItemByName(String token){
        //get the recyclerview
        RecyclerView recyclerView = findViewById(R.id.packlistrecyclerview);
        //create a list of items to display
        itemsToDisplay = repository.getPacklistItemByName(token, vacationID);
        System.out.println(itemsToDisplay.toString());
        //create the adapter
        final PacklistAdapter adapter = new PacklistAdapter(this);
        //set the adapter to the recyclerview
        recyclerView.setAdapter(adapter);
        //set the layoutmanager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set the list of items to the adapter
        adapter.setmPacklists(itemsToDisplay);
        resultsForTextView.setText(token);
        adapter.notifyDataSetChanged();


    }


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