package dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import entities.Packlist;

@Dao
public interface PacklistDAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Packlist packlist);
}
