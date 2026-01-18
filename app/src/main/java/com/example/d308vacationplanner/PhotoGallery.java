package com.example.d308vacationplanner;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.PhotoDAO;
import database.Repository;
import entities.Photo;
import entities.Vacation;

public class PhotoGallery extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ImageRecyclerData> recyclerDataArrayList;

    private List<Photo> existingPhotos;

    TextView galleryNameLabel;

    String galleryName;

    Button takePhotoButton;

    ImageView imageView;

    PhotoDAO mPhotoDAO;

    Repository repository;

    Photo photo;

    int vacationID;


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
        vacationID = getIntent().getIntExtra("vacationID", -1);
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
        repository = new Repository(getApplication());

        List<Photo> existingPhotos = repository.getmAssociatedPhotos(vacationID);
        //just checking that all the photos are in the list
        System.out.println(existingPhotos.toString());
        //The photos need to be shared to the recyclerAdapter so an intent can be started with the photo id

        if(existingPhotos!=null){
            for(Photo p: existingPhotos){
                ImageRecyclerData recyclerData = new ImageRecyclerData();
                byte[] bytesToBitmap = p.getBlob();
                //code to convert the byte array back to bitmap for display
                if(bytesToBitmap!= null & bytesToBitmap.length > 0){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytesToBitmap, 0, bytesToBitmap.length);
                    if(bitmap == null){
                        Toast.makeText(getApplicationContext(), "Couldn't load image", Toast.LENGTH_LONG).show();
                    }else{
                        recyclerData.setBitmap(bitmap);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Couldn't load image", Toast.LENGTH_LONG).show();
                }
                recyclerDataArrayList.add(recyclerData);
                ImageRecyclerAdapter adapter = new ImageRecyclerAdapter(recyclerDataArrayList, this);
                GridLayoutManager layoutManager = new GridLayoutManager(this, 3);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                adapter.setPhotos(existingPhotos);

            }
        }else{
            System.out.println("Null");
        }



    }//End of onCreate Method

    @Override
    protected void onResume(){

        super.onResume();
        List<Photo> allPhotos = repository.getmAssociatedPhotos(vacationID);
        RecyclerView recyclerView1 = findViewById(R.id.imagerecyclerview);
        final ImageRecyclerAdapter recyclerAdapter = new ImageRecyclerAdapter(this.recyclerDataArrayList, getApplicationContext());
        recyclerView1.setAdapter(recyclerAdapter);
        recyclerView1.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerAdapter.setPhotos(allPhotos);
        if(allPhotos.isEmpty()){

        }
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


            photo = new Photo();
            photo.setVacationID(this.vacationID);
            photo.setPhotoName("New Photo");
            photo.setBlob(getBytesFromBitmap(imageBitmap));
            imageData.setPhoto(photo);
            imageData.setImgid(photo.getPhotoID());

            repository.insert(photo);



            recyclerDataArrayList.add(imageData);






        }
    }
    //To-Do: Write a method here that converts the bitmap image to a BLOB and stores it to the database.
    public static byte[] getBytesFromBitmap(Bitmap bitmap){
        //output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        byte[] byteArray = outputStream.toByteArray();

        try{
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return byteArray;
    }
}