package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.LoginSettings;
import com.example.d308vacationplanner.R;

import dao.UserDAO;
import database.Repository;

public class MainActivity extends AppCompatActivity {
    static int numAttempts = 0;
    static int numAlert = 0;

    String userNameEntered;

    String passwordEntered;

    TextView loginSettings;

    public UserDAO userDAO;

    public Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText userNameField = findViewById(R.id.userNameField);

        EditText passwordField = findViewById(R.id.passwordField);
        //Setting up button with onclick
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameEntered = userNameField.getText().toString();
                passwordEntered = passwordField.getText().toString();
                if (numAttempts < 5) {
                    if(userExists()){
                        if(userNameFound(userNameEntered)){
                            if(passwordMatch(userNameEntered, passwordEntered)){
                                login();
                            }else{
                                Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "App not protected.", Toast.LENGTH_LONG).show();
                        login();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Too many failed attempts, please close application.", Toast.LENGTH_LONG).show();
                }
            }
        });
        repository = new Repository(getApplication());
        loginSettings = findViewById(R.id.loginSettings);
        loginSettings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, LoginSettings.class);
                startActivity(intent);
            }
        });
    }
    //Create a method for validating the username and password
    boolean userExists(){
        if(repository.getmAllUsers().isEmpty()){
            return false;
        }
        return true;
    }
    boolean userNameFound(String userNameEntered){
        try{
            if (repository.getmUserName(userNameEntered).equals(userNameEntered)) {
                return true;
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Invalid Username", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        return false;
    }

    boolean passwordMatch(String userNameEntered, String passwordEntered){
        if(repository.getmPassword(userNameEntered).equals(passwordEntered)){
            return true;
        }
        return false;
    }
    private void login(){
        Intent intent = new Intent(MainActivity.this, VacationList.class);
        intent.putExtra("test", "information sent");
        startActivity(intent);
    }




}