package com.example.d308vacationplanner.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;

import java.util.ArrayList;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageRecyclerViewHolder> {

    private ArrayList<ImageRecyclerData> photoGalleryArrayList;
    private Context context;

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
        }
    }
}
