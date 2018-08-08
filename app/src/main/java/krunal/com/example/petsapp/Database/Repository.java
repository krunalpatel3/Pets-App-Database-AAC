package krunal.com.example.petsapp.Database;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import java.util.List;

import krunal.com.example.petsapp.AppExecutor;
import krunal.com.example.petsapp.PetsModel;
import krunal.com.example.petsapp.UpdateViewModel;

/**
 * Created by acer on 13-03-2018.
 */

/**
 * Repository Class Where all DataBase operation are Define.
 */
public class Repository {

    private LiveData<List<PetsModel>> mPetsList;
    private AppDatabase mAppDatabase;
    private AppExecutor mAppExecutor;
    // To get class name
    private static final String TAB5 = Repository.class.getSimpleName();
    private PetsEntity mDetails;

    // Pets Gender values.
    public static final int GENDER_UNKNOWN = 0;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_FEMALE = 2;

    public Repository(Context context) {
        // Create Instance of AppExecutor class.
        this.mAppExecutor = new AppExecutor();
        // Create Instance of AppDatebase class
        this.mAppDatabase = AppDatabase.getInstance(context);
        // Create All List Pets From Database and store in LiveData<List<PetsModel>> mPetsList var.
        this.mPetsList = mAppDatabase.getDao().getPetsList();
    }

    /**
     * Query All Columns From the table Pets_tbl by id.
     * This is run on Background Thread.
     * @param id
     */
    public void getDetails(int id) {
        Log.i(TAB5, "getDetails call");
        mAppExecutor.diskIO().execute(() -> {
            // Get All data from store in mDeatils.
            mDetails = mAppDatabase.getDao().getpetdetail(id);
            UpdateViewModel.Name = mDetails.getName();
            UpdateViewModel.Breed = mDetails.getBreed();
            UpdateViewModel.gender = mDetails.getGender();
            UpdateViewModel.weigth = mDetails.getWeigth();
            Log.i(TAB5, String.valueOf(mDetails.getGender()));
        });
    }

    /**
     * Get Lists of Pets with name and breed.
     * @return LiveData mPetsList var.
     */
    public LiveData<List<PetsModel>> getPetsList() {
        return mPetsList;
    }

    /**
     * Insert pets into Database in table Pets_tbl.
     * Run on Background Thread.
     * @param petsEntity
     */

    public void Insert(PetsEntity petsEntity) {
        mAppExecutor.diskIO().execute(() -> {
            mAppDatabase.getDao().Insert(petsEntity);
        });
    }

    /**
     * Delete single pets entry into Database in table Pets_tbl.
     * Run on Background Thread.
     * @param petsEntity
     */
    public void Delete(PetsEntity petsEntity) {
        mAppExecutor.diskIO().execute(() -> {
            mAppDatabase.getDao().Delete(petsEntity);
        });
    }

    /**
     * Update single pets entry into Database in table Pets_tbl.
     * Run on Background Thread.
     * @param petsEntity
     */
    public void Update(PetsEntity petsEntity) {
        mAppExecutor.diskIO().execute(() -> {
            mAppDatabase.getDao().Update(petsEntity);
        });
    }

    /**
     * Delete All pets entry into Database in table Pets_tbl.
     * Run on Background Thread.
     */
    public void DeleteAll() {
        mAppExecutor.diskIO().execute(() -> {
            mAppDatabase.getDao().DeleteAll();
        });
    }


}
