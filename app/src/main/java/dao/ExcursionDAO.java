package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Excursion;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM excursions ORDER BY excursionID ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM excursions WHERE vacationID=:vacationID ORDER BY excursionID ASC")
    List<Excursion> getAssociatedExcursions(int vacationID);

   @Query("SELECT vacationName FROM vacations WHERE vacationID = :vacationID")
   String getVacationNameByID(int vacationID);

   @Query("SELECT vacationStartDate FROM vacations WHERE vacationID = :vacationID")
    String getVacationStartDateByID(int vacationID);

   @Query("SELECT vacationEndDate FROM vacations WHERE vacationID = :vacationID")
    String getVacationEndDateByID(int vacationID);


}
