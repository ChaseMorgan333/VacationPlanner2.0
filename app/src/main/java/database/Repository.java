package database;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.ExcursionDAO;
import dao.PhotoDAO;
import dao.UserDAO;
import dao.VacationDAO;
import entities.Excursion;
import entities.Photo;
import entities.User;
import entities.Vacation;

public class Repository {
    private UserDAO mUserDAO;

    private VacationDAO mVacationDAO;

    private List<Vacation> mAllVacations;

    private ExcursionDAO mExcursionDAO;

    private PhotoDAO mPhotoDAO;

    private List<Photo> mAssociatedPhotos;

    private byte[] mGetByteArrayFromDB;

    private List<Excursion> mAllExcursions;

    private List<Excursion> mAssociatedExcursions;

    private String mVacationName;

    private String mVacationStartDate;

    private String mVacationEndDate;

    private String mUserName;

    private String mPassword;

    private List<User> mAllUsers;

    private static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mVacationDAO = db.vacationDAO();
        mExcursionDAO = db.excursionDAO();
        mUserDAO = db.userDAO();
        mPhotoDAO = db.photoDAO();
    }

    public List<User> getmAllUsers(){
        databaseExecutor.execute(()->{
            mAllUsers = mUserDAO.getAllUsers();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllUsers;
    }

    public String getmPassword(String userName){
        databaseExecutor.execute(()->{
            mPassword = mUserDAO.getPasswordForUser(userName);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mPassword;
    }


    //gets a list of all vacations in the database
    public List<Vacation> getmAllVacations(){
        databaseExecutor.execute(()->{
            mAllVacations = mVacationDAO.getAllVacations();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllVacations;
    }

    public List<Excursion> getmAllExcursions(){
        databaseExecutor.execute(()->{
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public String getmVacationName(int vacationID){
        databaseExecutor.execute(()->{
            mVacationName = mExcursionDAO.getVacationNameByID(vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mVacationName;
    }

    public String getmUserName(String userName){
        databaseExecutor.execute(()->{
            mUserName = mUserDAO.findUserName(userName);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mUserName;
    }

    public String getmVacationStartDate(int vacationID){
        databaseExecutor.execute(()->{
            mVacationStartDate = mExcursionDAO.getVacationStartDateByID(vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mVacationStartDate;
    }

    public String getmVacationEndDate(int vacationID){
        databaseExecutor.execute(()->{
            mVacationEndDate = mExcursionDAO.getVacationEndDateByID(vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mVacationEndDate;
    }

    public List<Excursion> getmAssociatedExcursions(int mVacationID){
        databaseExecutor.execute(()->{
            mAssociatedExcursions = mVacationDAO.getAssociatedExcursions(mVacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAssociatedExcursions;
    }

    public List<Photo> getmAssociatedPhotos(int vacationID) {
        databaseExecutor.execute(()->{
            mAssociatedPhotos = mPhotoDAO.getAssociatedPhotos(vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAssociatedPhotos;
    }

    public void insert(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.insert(excursion);
        });
    }

    //inserts a vacation into the database
    public void insert(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.insert(vacation);
        });

    }

    //inserts a user into the database
    public void insert(User user){
        databaseExecutor.execute(()->{
            mUserDAO.insert(user);
        });
    }

    //inserts a photo into the database
    public void insert(Photo photo){
        databaseExecutor.execute(()->{
            mPhotoDAO.insert(photo);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //deletes a vacation from the database
    public void delete(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.deleteVacation(vacation);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    public void delete(int vacationID){
        databaseExecutor.execute(()->{
            mVacationDAO.deleteVacationByID(vacationID);
        });

    }

    public void deletePhoto(int vacationID){
        databaseExecutor.execute(()->{
            mPhotoDAO.deletePhoto(vacationID);
        });
    }

    public void delete(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.delete(excursion);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //updates and existing vacation in the database
    public void update(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.update(vacation);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(()->{
            mExcursionDAO.update(excursion);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(User user){
        databaseExecutor.execute(()->{
            update(user);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void updatePhotoName(String photoName, int photoID){
        databaseExecutor.execute(()->{
            mPhotoDAO.updatePhotoName(photoName, photoID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void updatePhotoLog(String photoLog, int photoID){
        databaseExecutor.execute(()->{
            mPhotoDAO.updatePhotoLog(photoLog, photoID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(String userName){
        databaseExecutor.execute(()->{
            mUserDAO.delete(userName);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public byte[] getmGetByteArrayFromDB() {
        return mGetByteArrayFromDB;
    }

    public void setmGetByteArrayFromDB(byte[] mGetByteArrayFromDB) {
        this.mGetByteArrayFromDB = mGetByteArrayFromDB;
    }





    public void setmAssociatedPhotos(List<Photo> mAssociatedPhotos) {
        this.mAssociatedPhotos = mAssociatedPhotos;
    }
}
