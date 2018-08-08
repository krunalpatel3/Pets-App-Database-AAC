package krunal.com.example.petsapp;

/**
 * Created by acer on 17-03-2018.
 */

/**
 * Class to get pet id,name,breed.
 */
public class PetsModel {

    private int pet_Id;

    private String pet_Name;

    private String pet_Breed;

    public PetsModel(int pet_Id, String pet_Name, String pet_Breed) {
        this.pet_Id = pet_Id;
        this.pet_Name = pet_Name;
        this.pet_Breed = pet_Breed;
    }

    public void setPet_Id(int pet_Id) {
        this.pet_Id = pet_Id;
    }

    public void setPet_Name(String pet_Name) {
        this.pet_Name = pet_Name;
    }

    public void setPet_Breed(String pet_Breed) {
        this.pet_Breed = pet_Breed;
    }

    public int getPet_Id() {
        return pet_Id;
    }

    public String getPet_Name() {
        return pet_Name;
    }

    public String getPet_Breed() {
        return pet_Breed;
    }
}
