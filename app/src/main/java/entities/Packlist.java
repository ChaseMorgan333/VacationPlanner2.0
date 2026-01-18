package entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "packlists")
public class Packlist {

    public Packlist(int vacationID, String category, String itemName, Boolean packed){
        this.vacationID = vacationID;
        this.category = category;
        this.itemName = itemName;
        this.packed = packed;
    }

    @PrimaryKey(autoGenerate = true)
    private int itemID;

    private int vacationID;

    private String category;

    private String itemName;

    private Boolean packed;


    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Boolean getPacked() {
        return packed;
    }

    public void setPacked(Boolean packed) {
        this.packed = packed;
    }
}
