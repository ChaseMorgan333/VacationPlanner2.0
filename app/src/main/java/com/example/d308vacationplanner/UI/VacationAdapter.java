package com.example.d308vacationplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;

import java.util.List;

import entities.Vacation;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {

    private List<Vacation> mVacations;

    private final Context context;

    private final LayoutInflater mInflater;

    public VacationAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout vacationItemView;
        private final TextView vacationNameTextView;
        private final TextView vacationStartDateTextView;
        private final TextView vacationEndDateTextView;

        public VacationViewHolder(@NonNull View itemView){
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.vacationListLinearLayout);
            vacationNameTextView = itemView.findViewById(R.id.vacationNameTextView);
            vacationStartDateTextView = itemView.findViewById(R.id.vacationListViewStartDate);
            vacationEndDateTextView = itemView.findViewById(R.id.vacationListViewEndDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Vacation current = mVacations.get(position);
                    Intent intent = new Intent(context, VacationDetails.class);
                    intent.putExtra("id", current.getVacationID());
                    intent.putExtra("name", current.getVacationName());
                    intent.putExtra("accommodation", current.getAccommodation());
                    intent.putExtra("startDate", current.getVacationStartDate());
                    intent.putExtra("endDate", current.getVacationEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation_list_item, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if(mVacations!=null) {
            Vacation current = mVacations.get(position);
            String name = current.getVacationName();

            holder.vacationNameTextView.setText(current.getVacationName());
            holder.vacationStartDateTextView.setText(current.getVacationStartDate());
            holder.vacationEndDateTextView.setText(current.getVacationEndDate());
        }else{
            //holder.vacationItemView.setText("No vacations booked.");
        }
    }

    @Override
    public int getItemCount() {
        if(mVacations!=null){
            return mVacations.size();
        }
        else return 0;
    }

    public void setVacations(List<Vacation> vacations){
        mVacations = vacations;
        notifyDataSetChanged();
    }
}
