package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;

    private String vacationName;



    public Vacation(int vacationID, String vacationName){

        this.vacationID = vacationID;
        this.vacationName = vacationName;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    @NonNull
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(this.vacationID);
        builder.append(this.vacationName);
        return builder.toString();
    }


}
