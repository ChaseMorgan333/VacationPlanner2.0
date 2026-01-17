package com.example.d308vacationplanner;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.UI.ImageRecyclerAdapter;
import com.example.d308vacationplanner.UI.ImageRecyclerData;

import java.util.ArrayList;

import dao.PhotoDAO;
import database.Repository;

public class PhotoGallery extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ImageRecyclerData> recyclerDataArrayList;

    TextView galleryNameLabel;

    String galleryName;

    Button takePhotoButton;

    ImageView imageView;

    PhotoDAO mPhotoDAO;

    Repository repository;




    private static final int CAMERA_REQUEST_CODE = 100; // Define a request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_photo_gallery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        galleryNameLabel = findViewById(R.id.vacationnamegallerylabel2);
        galleryName = getIntent().getStringExtra("vacationName");
        galleryNameLabel.setText(galleryName);
        recyclerView = findViewById(R.id.imagerecyclerview);
        takePhotoButton = findViewById(R.id.takephotobutton);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        //This list holds bitmap images but needs to be coded to the database for persistence.
        recyclerDataArrayList = new ArrayList<>();






    }
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        } catch (SecurityException securityException){
            Toast.makeText(getApplicationContext(), "You must allow camera permission in settings.", Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Some error occurred" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageRecyclerData imageData = new ImageRecyclerData();
            imageData.setBitmap(imageBitmap);




            recyclerDataArrayList.add(imageData);



            ImageRecyclerAdapter adapter = new ImageRecyclerAdapter(recyclerDataArrayList, this);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);


        }
    }
}