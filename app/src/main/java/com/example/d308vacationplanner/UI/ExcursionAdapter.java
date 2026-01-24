package com.example.d308vacationplanner.UI;

import static android.view.View.VISIBLE;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;

import java.util.List;

import database.Repository;
import entities.Excursion;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;



    public ExcursionAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        private final TextView excursionDateTimeView;

        private final TextView excursionTimeView;

        private final TextView noExcursionsTextView;



        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.excursionListTextView);
            excursionDateTimeView = itemView.findViewById(R.id.datetimeexcursionview);
            noExcursionsTextView = itemView.findViewById(R.id.noexcursionstextview);
            excursionTimeView = itemView.findViewById(R.id.localtimeexcursionview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    final Excursion current = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("excursionID", current.getExcursionID());
                    intent.putExtra("vacationID", current.getVacationID());
                    intent.putExtra("excursionName", current.getExcursionName());
                    intent.putExtra("excursionDate", current.getExcursionDate());
                    intent.putExtra("excursionTime", current.getExcursionTime());


                    context.startActivity(intent);

                }
            });
        }
    }
    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if(mExcursions!=null){
            Excursion current = mExcursions.get(position);
            String excursionName = current.getExcursionName();
            String excursionDateTime = current.getExcursionDate();
            String excursionTime = current.getExcursionTime();
            holder.excursionTimeView.setText(excursionTime);
            holder.excursionItemView.setText(excursionName);
            holder.excursionDateTimeView.setText(excursionDateTime);
        }else { }

    }

    public void setExcursions(List<Excursion> excursions){
        mExcursions = excursions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mExcursions!=null){
            return mExcursions.size();
        }else {
            return 0;
        }
    }
}
