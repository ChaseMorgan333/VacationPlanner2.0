package database;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.ExcursionDAO;
import dao.VacationDAO;
import entities.Excursion;
import entities.Vacation;

public class Repository {

    private VacationDAO mVacationDAO;

    private List<Vacation> mAllVacations;

    private ExcursionDAO mExcursionDAO;

    private List<Excursion> mAllExcursions;

    private List<Excursion> mAssociatedExcursions;

    private String mVacationName;

    private String mVacationStartDate;

    private String mVacationEndDate;

    private static int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mVacationDAO = db.vacationDAO();
        mExcursionDAO = db.excursionDAO();
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
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(()->{
            mExcursionDAO.update(excursion);
        });
    }
}
