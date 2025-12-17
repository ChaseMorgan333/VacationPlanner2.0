package com.example.d308vacationplanner.UI;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StartDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    VacationDetails fragment;
    StartDatePickerFragment(VacationDetails fragment){
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(requireContext(), this, year, month, day);
    }

    private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("M/dd/yyyy", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("MM/d/yyyy", Locale.ENGLISH)
    );

    public static LocalDate parseDate(String dateString){
        for(DateTimeFormatter formatter : FORMATTERS){
            try{
                return LocalDate.parse(dateString, formatter);
            }catch (DateTimeParseException e){

            }
        }
        throw new IllegalArgumentException("Unsupported date format");
    }


    //Create a method to validate that the start date is in the future
    private boolean futureDate(String date){


       try{
           LocalDate formattedDate = parseDate(date);

           if(formattedDate.isAfter(LocalDate.now())||formattedDate.isEqual(LocalDate.now())){
               return true;
           }else{
               Toast.makeText(this.getContext(), "The date entered is in the past", Toast.LENGTH_LONG).show();
               return false;
           }
       }catch (DateTimeParseException  | IllegalArgumentException exception){

               Toast.makeText(this.getContext(),"Invalid Date.", Toast.LENGTH_LONG).show();


       }

        return true;

    }

    private boolean endDateAfterStart(){
        String startDate = this.fragment.vacationStartDate.getText().toString();
        String endDate = this.fragment.vacationEndDate.getText().toString();
            if(!endDate.isEmpty()) {
                //now check if the end date selected is after the start.
                LocalDate startDateLocal = parseDate(startDate);
                LocalDate endDateLocal = parseDate(endDate);
                if(endDateLocal.isAfter(startDateLocal)||endDateLocal.equals(startDateLocal)){
                    return true;
                }
            }else{
                return true;
            }
         return false;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        StringBuilder builder = new StringBuilder();

        builder.append((month +1) + "/");
        builder.append(Integer.toString(dayOfMonth) + "/");
        builder.append(Integer.toString(year));
        String formattedDate = builder.toString();
        String endDate = this.fragment.vacationEndDate.getText().toString();

        //Verify that the selected date is either today or in the future.
        if(futureDate(formattedDate)){
            this.fragment.vacationStartDate.setText(formattedDate);
            if(endDateAfterStart()){

            }else{
                this.fragment.vacationEndDate.setText("");
                Toast.makeText(this.getContext(), "End date must be after start date.", Toast.LENGTH_LONG).show();
            }

        }

    }
}
