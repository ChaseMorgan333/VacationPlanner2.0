package com.example.d308vacationplanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import database.Repository;


public class PhotoDetails extends AppCompatActivity {

    EditText editTextPhotoName;
    EditText editTextPhotoLog;

    Repository repository;

    private int photoID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_photo_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        WindowCompat.setDecorFitsSystemWindows(getWindow(),true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        byte[] byteArray = getIntent().getByteArrayExtra("picture");
        //Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();

            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ImageView imageView = findViewById(R.id.imageViewFull);
            imageView.setImageBitmap(bitmap);
        editTextPhotoName = findViewById(R.id.photonamelabel);
        editTextPhotoName.setText(getIntent().getStringExtra("name"));
        editTextPhotoName.setEnabled(false);
        editTextPhotoLog = findViewById(R.id.editPhotoLog);
        editTextPhotoLog.setEnabled(false);
        editTextPhotoLog.setText(getIntent().getStringExtra("photoLog"));
        repository = new Repository(getApplication());
        photoID = getIntent().getIntExtra("photoID", -1);





    }//end oncreate



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.photo_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        if (item.getItemId() == android.R.id.home) {
                this.finish();
        }


        if(item.getItemId()==R.id.editdetails){
            editTextPhotoName.setEnabled(true);
            editTextPhotoLog.setEnabled(true);
        }

        if(item.getItemId()==R.id.savedetails){
            repository.updatePhotoName(editTextPhotoName.getText().toString(), photoID);
            repository.updatePhotoLog(editTextPhotoLog.getText().toString(), photoID);
            this.finish();
        }

        if(item.getItemId()==R.id.deletephoto){
            repository.deletePhoto(photoID);
            this.finish();
        }
        return true;
    }



}