package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
    void deletePhoto(Photo photo);

    @Query("DELETE FROM PHOTOS WHERE vacationID = :vacationID")
    void deletePhoto(int vacationID);

    @Query("UPDATE PHOTOS SET PHOTONAME = :photoName WHERE photoID = :photoID")
    void updatePhotoName(String photoName, int photoID);

    @Query("UPDATE PHOTOS SET PHOTOLOG = :photoLog WHERE photoID = :photoID")
    void updatePhotoLog(String photoLog, int photoID);


}
