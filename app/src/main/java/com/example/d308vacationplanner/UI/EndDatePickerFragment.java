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
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EndDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    VacationDetails fragment;
    EndDatePickerFragment(VacationDetails fragment){
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

    //create a list of DateTimeFormatters that can be iterated to find the correct date format
    private static final List<DateTimeFormatter> FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("MM/dd/yyyy", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("M/dd/yyyy", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("M/d/yyyy", Locale.ENGLISH),
            DateTimeFormatter.ofPattern("MM/d/yyyy", Locale.ENGLISH)
    );
    //a method that attempts to parse the string date input using the list of formatters and returns
    //the LocalDate if successful or throws an illegal argument exception.
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
        }catch (DateTimeParseException | IllegalArgumentException exception){
            Toast.makeText(this.getContext(),"Invalid Date.", Toast.LENGTH_LONG).show();
        }

        return true;

    }

    //Since this fragment is for picking the end date we need to verify that a start date
    //has been selected and that the end date is after that.
    private boolean endDateAfterStart(String endDate){
        String startDate = this.fragment.vacationStartDate.getText().toString();
        //first check if the start date has been set.
        if(startDate.isEmpty()){
            Toast.makeText(this.getContext(), "Start date must be set first", Toast.LENGTH_LONG).show();
        }else{
            //now check if the end date selected is after the start.
            LocalDate startDateLocal = parseDate(startDate);
            LocalDate endDateLocal = parseDate(endDate);
            if(endDateLocal.isAfter(startDateLocal)||endDateLocal.equals(startDateLocal)){
                return true;
            }else{
                Toast.makeText(this.getContext(), "End date must be after start date", Toast.LENGTH_LONG).show();
            }
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
        //First check that the date is either today or in the future.
        if(futureDate(formattedDate)){
            if(endDateAfterStart(formattedDate)) {
                this.fragment.vacationEndDate.setText(formattedDate);
            }
        }

    }
}
