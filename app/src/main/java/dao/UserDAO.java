package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entities.User;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Update
    void update(User user);

    @Query("DELETE FROM USER WHERE userName = :userName")
    void delete(String userName);

    @Query("SELECT password FROM USER WHERE userName = :userName")
    String getPasswordForUser(String userName);

    @Query("SELECT userName FROM USER WHERE userName = :userName")
    String findUserName(String userName);

    @Query("select * from user")
    List<User> getAllUsers();
}
