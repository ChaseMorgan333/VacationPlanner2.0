package dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import entities.Packlist;

@Dao
public interface PacklistDAO {

    //The dao needs to insert new packlist items into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Packlist packlist);

    //The dao needs to retrieve packlist items from the database using only the item CATEGORY;
    @Query("SELECT * FROM packlists WHERE CATEGORY = :category")
    List<Packlist> getPacklistItemByCategory(String category);

    //The dao needs to retrieve packlist items from the database using only the search token
    @Query("SELECT * FROM packlists WHERE itemName LIKE :token")
    List<Packlist> getPacklistItemByToken(String token);

    //The dao needs to delete packlist items and should probably do that using the primary key
    @Query("DELETE FROM packlists WHERE itemID = :itemID")
    void deletePacklist(int itemID);

    //select all the records for display
    @Query(("SELECT * FROM packlists"))
    List<Packlist> allRecords();
}
