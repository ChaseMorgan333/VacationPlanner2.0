package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import entities.Photo;


@Dao
public interface PhotoDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Photo photo);

    @Query("SELECT * FROM photos WHERE vacationID = :vacationID")
    List<Photo> getAssociatedPhotos(int vacationID);

    @Query("SELECT blob FROM PHOTOS WHERE photoID = :photoID")
    byte[] getByteArrayFromDB(int photoID);

    @Delete
    void delete(Photo photo);

}
