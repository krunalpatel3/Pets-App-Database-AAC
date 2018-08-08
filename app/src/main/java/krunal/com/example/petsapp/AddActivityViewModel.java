package krunal.com.example.petsapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import krunal.com.example.petsapp.Database.PetsEntity;
import krunal.com.example.petsapp.Database.Repository;

/**
 * Created by acer on 13-03-2018.
 */

/**
 * viewmodel model view class for AddActivity.
 */
public class AddActivityViewModel extends AndroidViewModel {

    static int GENDER = Repository.GENDER_UNKNOWN;
    private Repository mRepository;

    public AddActivityViewModel(@NonNull Application application) {
        super(application);
        // Initialization of Repository.
        this.mRepository = new Repository(application);
    }

    /**
     * Insert Pets entry into Pets_tbl.
     * @param petsEntity
     */
    void Insert(PetsEntity petsEntity){
        mRepository.Insert(petsEntity);
    }


}
