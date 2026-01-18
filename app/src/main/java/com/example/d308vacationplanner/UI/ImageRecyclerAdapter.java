package com.example.d308vacationplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.PhotoDetails;
import com.example.d308vacationplanner.R;

import java.util.ArrayList;
import java.util.List;

import entities.Photo;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageRecyclerViewHolder> {

    private ArrayList<ImageRecyclerData> photoGalleryArrayList;
    private Context context;

    private List<Photo> photos;

    public ImageRecyclerAdapter(ArrayList<ImageRecyclerData> recyclerDataArrayList, Context mContext){
        this.photoGalleryArrayList = recyclerDataArrayList;
        this.context = mContext;
    }

    @NonNull
    @Override
    public ImageRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new ImageRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageRecyclerViewHolder holder, int position){
        ImageRecyclerData recyclerData = photoGalleryArrayList.get(position);

        holder.imageView.setImageBitmap(recyclerData.getBitmap());


    }

    @Override
    public int getItemCount(){
        return photoGalleryArrayList.size();
    }

    public class ImageRecyclerViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public ImageRecyclerViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Photo p = photos.get(position);

                    Intent intent = new Intent(context, PhotoDetails.class);
                    intent.putExtra("picture", p.getBlob());
                    intent.putExtra("name", p.getPhotoName());
                    intent.putExtra("photoID", p.getPhotoID());
                    intent.putExtra("photoLog", p.getPhotoLog());
                    context.startActivity(intent);
                }
            });
        }
    }


    public void setPhotos(List<Photo> photos){
        this.photos = photos;
        notifyDataSetChanged();
    }
}
