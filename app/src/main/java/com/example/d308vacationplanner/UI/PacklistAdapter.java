package com.example.d308vacationplanner.UI;

import com.example.d308vacationplanner.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import entities.Packlist;

public class PacklistAdapter extends RecyclerView.Adapter<PacklistAdapter.PacklistViewHolder> {
    private List<Packlist> mPacklists;
    private final Context context;
    private final LayoutInflater mInflater;

    public PacklistAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public PacklistAdapter.PacklistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.packlist_item, parent, false);
        return new PacklistViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PacklistAdapter.PacklistViewHolder holder, int position) {
        if(mPacklists!=null){
            Packlist current = mPacklists.get(position);
            String name = current.getItemName();
            String category = current.getCategory();
            String packedYN;
            Boolean packed = current.getPacked();
            if(packed){
                packedYN = "Yes";
            }else{
                packedYN = "No";
            }
            holder.itemNameTextView.setText(name);
            holder.itemCategoryTextView.setText(category);
            holder.itemPackedTextView.setText(packedYN);
        }
    }

    @Override
    public int getItemCount(){
        if(mPacklists!=null){
            return mPacklists.size();
        }
        else return 0;
    }

    public class PacklistViewHolder extends RecyclerView.ViewHolder{
            private final LinearLayout packlistItemView;
            private final TextView itemNameTextView;
            private final TextView itemCategoryTextView;

            private final TextView itemPackedTextView;
            private final Button deleteItembButton;

            public PacklistViewHolder(@NonNull View itemView){
                super(itemView);
                packlistItemView = itemView.findViewById(R.id.packlistlinearlayout);
                itemNameTextView = itemView.findViewById(R.id.textViewItemNameHolder);
                itemCategoryTextView = itemView.findViewById(R.id.textViewItemCategoryHolder);
                itemPackedTextView = itemView.findViewById(R.id.textViewItemPackedHolder);
                deleteItembButton = itemView.findViewById(R.id.deletepacklistitembutton);
                deleteItembButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();

                    }
                });
            }
    }


    public void setmPacklists(List<Packlist> packlists){
        mPacklists = packlists;
        notifyDataSetChanged();
    }
}
