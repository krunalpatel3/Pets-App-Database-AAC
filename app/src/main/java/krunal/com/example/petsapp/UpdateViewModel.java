package krunal.com.example.petsapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import krunal.com.example.petsapp.Database.PetsEntity;
import krunal.com.example.petsapp.Database.Repository;

/**
 * Created by acer on 20-03-2018.
 */
/**
 * viewmodel model view class for UpdateActivity.
 */
public class UpdateViewModel extends AndroidViewModel {

    private Repository mRepository;
    static int GENDER = Repository.GENDER_UNKNOWN;

    // vars to store pets entry and display in ui.
    public static String Name;
    public static String Breed;
    public static int gender;
    public static String weigth;

    public UpdateViewModel(@NonNull Application application) {
        super(application);
        // Initialization of Repository.
        this.mRepository = new Repository(application);
    }

    /**
     * Query Pets from the Pets_tbl by id.
     * @param id
     */
    public void getDetails(int id) {
        mRepository.getDetails(id);
    }

    /**
     * Update single pets from Pets_tbl.
     * @param petsEntity
     */
    void Update(PetsEntity petsEntity){
       mRepository.Update(petsEntity);
    }

    /**
     * Delete single pets from Pets_tbl.
     * @param petsEntity
     */
    void Delete(PetsEntity petsEntity){
        mRepository.Delete(petsEntity);
    }
}
