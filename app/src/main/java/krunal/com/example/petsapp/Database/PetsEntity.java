package krunal.com.example.petsapp.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by acer on 13-03-2018.
 */

/**
 * Entity class where table name and columns are define.
 * Its a POJO Class.
 */
@Entity(tableName = "Pets_tbl")
public class PetsEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pet_Id")
    private int id;

    @ColumnInfo(name = "pet_Name")
    private String name;

    @ColumnInfo(name = "pet_Breed")
    private String breed;

    @ColumnInfo(name = "Gender")
    private int gender;

    @ColumnInfo(name = "pet_Weigth")
    private String weigth;

    @Ignore
    public PetsEntity(int id, String name, String breed,int gender, String weigth) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.weigth = weigth;
    }

    public PetsEntity(String name, String breed, int gender, String weigth) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.weigth = weigth;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getGender() {
        return gender;
    }

    public String getWeigth() {
        return weigth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setWeigth(String weigth) {
        this.weigth = weigth;
    }
}
