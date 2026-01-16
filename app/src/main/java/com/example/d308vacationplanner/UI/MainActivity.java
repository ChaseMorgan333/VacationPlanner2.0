package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

import com.example.d308vacationplanner.R;

public class MainActivity extends AppCompatActivity {
    static int numAttempts = 0;
    static int numAlert = 0;
    static final String userName = "Chase";
    static final String password = "Adventure333*";

    String userNameEntered;

    String passwordEntered;

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
                    if (userNameMatch(userNameEntered) && passwordMatch(passwordEntered)) {
                        login();
                    } else {
                        numAttempts++;
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Too many failed attempts, please close application.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //Create a method for validating the username and password
    private boolean userNameMatch(String userNameEntered){
        if(userNameEntered.equals(userName)){
            return true;
        }else {
            Toast.makeText(getApplicationContext(), "Username does not match.", Toast.LENGTH_LONG).show();
            return false;
        }
    }
    private boolean passwordMatch(String passwordEntered){
        if(passwordEntered.equals(password)){
            return true;
        }else{
            Toast.makeText(getApplicationContext(), "Password does not match.", Toast.LENGTH_LONG).show();
            return false;
        }

    }
    private void login(){
        Intent intent = new Intent(MainActivity.this, VacationList.class);
        intent.putExtra("test", "information sent");
        startActivity(intent);
    }




}