package com.example.d308vacationplanner;

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

import java.lang.annotation.Repeatable;
import java.util.List;

import database.Repository;
import entities.User;

public class LoginSettings extends AppCompatActivity {

    public Repository repository;

    private EditText currentUserName;

    private EditText currentPassword;

    private EditText newUserName;

    private EditText newPassword;

    private Button update;

    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        repository = new Repository(getApplication());
        currentUserName = findViewById(R.id.currentusername);
        currentPassword = findViewById(R.id.currentpassword);
        newUserName = findViewById(R.id.newusername);
        newPassword = findViewById(R.id.newpassword);
        update = findViewById(R.id.updatebutton);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repository.getmAllUsers().isEmpty()){
                    //creating a new user
                    if(isValidPassword(newPassword.getText().toString())&&isValidUserName(newUserName.getText().toString())) {
                        User user = new User(newUserName.getText().toString(), newPassword.getText().toString());
                        repository.insert(user);
                        finish();
                    }
                }else{
                    users = repository.getmAllUsers();
                    if(users.get(0).userName.equals(currentUserName.getText().toString())){
                        if(users.get(0).password.equals(currentPassword.getText().toString())){
                            if(isValidUserName(newUserName.getText().toString())&&isValidPassword(newPassword.getText().toString())){

                                repository.delete(currentUserName.getText().toString());
                                repository.insert(new User(newUserName.getText().toString(),newPassword.getText().toString()));
                                Toast.makeText(getApplicationContext(), "User credentials upadated.", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Invalid username.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    //Check if username is valid
    boolean isValidUserName(String userName){
        if(userName.length()<8){
            Toast.makeText(getApplicationContext(), "Username must be at least 8 characters.",Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }
    //Check if password is valid
    boolean isValidPassword(String password) {
        if (password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}