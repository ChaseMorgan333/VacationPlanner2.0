package database;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dao.ExcursionDAO;
import dao.PacklistDAO;
import dao.PhotoDAO;
import dao.UserDAO;
import dao.VacationDAO;
import entities.Excursion;
import entities.Packlist;
import entities.Photo;
import entities.User;
import entities.Vacation;

import android.content.Context;

@Database(entities = {Vacation.class, Excursion.class, User.class, Photo.class, Packlist.class}, version = 14, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase{
    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();

    public abstract UserDAO userDAO();

    public abstract PhotoDAO photoDAO();

    public abstract PacklistDAO packlistDAO();


    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (VacationDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VacationDatabaseBuilder.class, "MyVacationDatabase.db" )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
