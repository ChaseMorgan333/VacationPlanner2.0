package dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.Packlist;

@Dao
public interface PacklistDAO {

    //The dao needs to insert new packlist items into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Packlist packlist);

    //The dao needs to retrieve packlist items from the database using only the item CATEGORY;
    @Query("SELECT * FROM packlists WHERE vacationID = :vacationID AND CATEGORY = :category")
    List<Packlist> getPacklistItemByCategory(String category, int vacationID);

    //The dao needs to retrieve packlist items from the database using only the search token
    @Query("SELECT * FROM packlists WHERE vacationID = :vacationID AND itemName LIKE  '%'|| :token || '%'")
    List<Packlist> getPacklistItemByToken(String token, int vacationID);

    //The dao needs to delete packlist items and should probably do that using the primary key
    @Query("DELETE FROM packlists WHERE vacationID = :vacationID AND itemID = :itemID")
    void deletePacklist(int itemID, int vacationID);

    //select all the records for display
    @Query(("SELECT * FROM packlists WHERE vacationID = :vacationID"))
    List<Packlist> allRecords(int vacationID);

    //query to select all the packed items
    @Query("SELECT * FROM packlists WHERE vacationID = :vacationID AND packed = 1")
    List<Packlist> getPackedItems(int vacationID);

    //query to select all the unpacked items
    @Query("SELECT * FROM packlists WHERE vacationID = :vacationID AND packed = 0")
    List<Packlist> getUnpackedItems(int vacationID);

    @Update
    void updatePacklistItem(Packlist item);
}
