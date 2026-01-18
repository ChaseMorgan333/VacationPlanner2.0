package com.example.d308vacationplanner.UI;

import android.graphics.Bitmap;
import android.widget.ImageView;

import entities.Photo;

public class ImageRecyclerData {

    private ImageView imageView;

    private int imgid;

    private Bitmap bitmap;


    private Photo photo;



    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
