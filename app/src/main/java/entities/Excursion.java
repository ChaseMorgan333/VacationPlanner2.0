package entities;

import android.app.Application;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import database.Repository;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey
    private int excursionID;

    private int vacationID;

    private String excursionName;

    private String excursionDate;





    public Excursion(int vacationID, int excursionID, String excursionName, String excursionDate){
        this.vacationID = vacationID;
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.excursionDate = excursionDate;


    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }
}
