package krunal.com.example.petsapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Update;
import java.util.List;
import krunal.com.example.petsapp.PetsModel;

/**
 * Created by acer on 13-03-2018.
 */
@Dao
public interface PetsDao {

    // Its also shows you how to query specifically columns from the table using Room persistence library.
    // Query for to get pet_id,pet_Name,pet_Breed from Pets_tbl.
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("select pet_Id,pet_Name,pet_Breed From Pets_tbl")
    LiveData<List<PetsModel>> getPetsList();

    // Query all columns from the table Pets_tbl by id.
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT pet_Id,pet_Name,pet_Breed,Gender,pet_Weigth FROM Pets_tbl WHERE pet_Id = :id")
    PetsEntity getpetdetail(int id);

    // Insert Pets entry into table Pets_tbl.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(PetsEntity petsEntity);

    // Delete single Pets entry from the table Pets_tbl.
    @Delete
    void Delete(PetsEntity petsEntity);

    // Update single Pets entry from the table Pet_tbl.
    @Update
    void Update(PetsEntity petsEntity);

    // Delete all Pets entry from the table Pet_tbl.
    @Query("Delete From Pets_tbl")
    void DeleteAll();


}
