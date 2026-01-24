package entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @NonNull
    @PrimaryKey
    public String userName;
    public String password;

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }


}
