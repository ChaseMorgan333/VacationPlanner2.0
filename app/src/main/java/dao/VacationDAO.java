package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Excursion;
import entities.Vacation;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void deleteVacation(Vacation vacation);

    @Query("DELETE FROM vacations WHERE vacationID = :ID")
    void deleteVacationByID(int ID);

    @Query("SELECT * FROM VACATIONS ORDER BY vacationID ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM EXCURSIONS WHERE vacationID = :vacationID ORDER BY excursionDate ASC")
    List<Excursion> getAssociatedExcursions(int vacationID);



}
