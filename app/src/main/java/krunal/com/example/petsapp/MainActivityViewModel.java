package krunal.com.example.petsapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;

import krunal.com.example.petsapp.Database.PetsEntity;
import krunal.com.example.petsapp.Database.Repository;

/**
 * Created by acer on 13-03-2018.
 */

/**
 * viewmodel model view class for MainActivity.
 */
public class MainActivityViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<PetsModel>> mlist;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        // Initialization of Repository.
        this.mRepository = new Repository(application);
        // Get All List of pet from Datebase and store in LiveData<List<PetsModel>> mlist.
        this.mlist = mRepository.getPetsList();
    }

    /**
     * Get All List of Pets from Datebase.
     * @return mlist
     */
    LiveData<List<PetsModel>> getpetslist(){
        return mlist;
    }

    /**
     * Insert pet entry in Database.
     * @param petsEntity
     */
    void Add(PetsEntity petsEntity){
        mRepository.Insert(petsEntity);
    }

    /**
     * Delete All pet entry from database.
     */
    void DeleteAll(){
        mRepository.DeleteAll();
    }


}
