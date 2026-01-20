package database;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

public class Repository {
    private UserDAO mUserDAO;

    private VacationDAO mVacationDAO;

    private List<Vacation> mAllVacations;

    private ExcursionDAO mExcursionDAO;

    private PhotoDAO mPhotoDAO;

    private PacklistDAO mPacklistDAO;

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

    private List<Packlist> mAssociatedPackItems;

    private List<Packlist> mAssociatedPackItems2;

    private List<Packlist> mAllPackItems;

    private List<Packlist> mGetPackedItems;

    private List<Packlist> mGetUnpackedItems;

    private static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mVacationDAO = db.vacationDAO();
        mExcursionDAO = db.excursionDAO();
        mUserDAO = db.userDAO();
        mPhotoDAO = db.photoDAO();
        mPacklistDAO = db.packlistDAO();
    }

    //-----------------------THIS SECTION IS FOR VACATIONS--------------------------//
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

    //inserts a vacation into the database
    public void insert(Vacation vacation){
        databaseExecutor.execute(()->{
            mVacationDAO.insert(vacation);
        });

    }

    //-----------------------________END_VACATIONS________--------------------------//

    //-----------------------THIS SECTION IS FOR EXCURSIONS--------------------------//
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

    public void insert(Excursion excursion){
        databaseExecutor.execute(()->{
            mExcursionDAO.insert(excursion);
        });
    }
    //-----------------------________END_EXCURSIONS________--------------------------//
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
    //-----------------------THIS SECTION IS FOR USERS--------------------------//
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
    //-----------------------________END_USERS________--------------------------//
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
    //-----------------------THIS SECTION IS FOR PHOTOS--------------------------//
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
    //-----------------------________END_PHOTOS________--------------------------//

    //-----------------------THIS SECTION IS FOR PACKLISTS--------------------------//
    //repo method for inserting items.
    public void insertNewPacklistItem(Packlist itemToAdd){
        databaseExecutor.execute(()->{
            mPacklistDAO.insert(itemToAdd);
        });
    }
    //repo method for selecting a list of items based only on their category.
    public List<Packlist> getPacklistItemByCategory(String category, int vacationID){
        databaseExecutor.execute(()->{
            mAssociatedPackItems = mPacklistDAO.getPacklistItemByCategory(category, vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAssociatedPackItems;
    }
    //repo method for selecting a list of items based only on their name.
    public List<Packlist> getPacklistItemByName(String token, int vacationID){
        databaseExecutor.execute(()->{
            mAssociatedPackItems2 = mPacklistDAO.getPacklistItemByToken(token, vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAssociatedPackItems2;
    }

    public List<Packlist> getAllItems(int vacationID){
        databaseExecutor.execute(()->{
            mAllPackItems = mPacklistDAO.allRecords(vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllPackItems;
    }
    //
    public List<Packlist> getPackedItems(int vacationID){
        databaseExecutor.execute(()->{
            mGetPackedItems = mPacklistDAO.getPackedItems(vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mGetPackedItems;
    }
    //
    public List<Packlist> getGetUnpackedItems(int vacationID){
        databaseExecutor.execute(()->{
            mGetUnpackedItems = mPacklistDAO.getUnpackedItems(vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mGetUnpackedItems;
    }

    public void updatePacklistItem(Packlist item){
        databaseExecutor.execute(()->{
            mPacklistDAO.updatePacklistItem(item);
        });
    }

    public void deletePackListItem(int itemID, int vacationID){
        databaseExecutor.execute(()->{
            mPacklistDAO.deletePacklist(itemID, vacationID);
        });
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    //-----------------------________END_PACKLISTS________--------------------------//










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

    public void deletePhoto(int photoID){
        databaseExecutor.execute(()->{
            mPhotoDAO.deletePhoto(photoID);
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







}
