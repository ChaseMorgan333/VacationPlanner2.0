package com.example.d308vacationplanner.UI;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageRecyclerData {

    private ImageView imageView;

    private int imgid;

    private Bitmap bitmap;



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
}
